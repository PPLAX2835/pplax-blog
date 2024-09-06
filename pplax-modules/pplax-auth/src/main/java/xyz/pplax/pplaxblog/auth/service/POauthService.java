package xyz.pplax.pplaxblog.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import xyz.pplax.pplaxblog.auth.exception.POauthException;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.commons.constants.SiteSettingConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.CaptchaUtils;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.feign.AuthFeignClient;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.redis.AuthRedisConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.UserSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.CaptchaDto;
import xyz.pplax.pplaxblog.xo.dto.EditPasswordDto;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.dto.RegisterDto;
import xyz.pplax.pplaxblog.xo.dto.edit.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;
import xyz.pplax.pplaxblog.xo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class POauthService {

    @Autowired
    private UserService userService;

    @Autowired
    private SiteSettingService siteSettingService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private TokenStore redisTokenStore;

    @Value("${pplax.oauth.client-id:pplax}")
    private String clientId;

    @Value("${pplax.oauth.client-secret:pplax123456}")
    private String clientSecret;


    /**
     * 拼图验证码允许偏差
     **/
    private static final Integer ALLOW_DEVIATION = 3;

    /**
     * 校验验证码
     * @param imageKey
     * @param imageCode
     * @return boolean
     **/
    public Boolean checkImageCode(String imageKey, String imageCode) {
        String text = redisService.getCacheObject(AuthRedisConstants.IMAGE_CODE + AuthRedisConstants.SEGMENTATION + imageKey);
        if(org.apache.commons.lang3.StringUtils.isBlank(text)){
            throw new POauthException(HttpStatus.VALIDATION_CODE_EXPIRED);
        }
        // 根据移动距离判断验证是否成功
        if (Math.abs(Integer.parseInt(text) - Integer.parseInt(imageCode)) > ALLOW_DEVIATION) {
            throw new POauthException(HttpStatus.VALIDATION_FAIL);
        }

        return true;
    }


    /**
     * 登录操作时的获取token
     * @param httpServletRequest
     * @param loginDto
     * @return
     */
    public OAuth2AccessToken getToken(HttpServletRequest httpServletRequest, LoginDto loginDto) throws HttpRequestMethodNotSupportedException {
        // 先检查验证码
        checkImageCode(loginDto.getNonceStr() + IpUtils.getIpAddress(httpServletRequest), loginDto.getValue());

        //创建客户端信息,客户端信息可以写死进行处理，因为Oauth2密码模式，客户端双信息必须存在，所以伪装一个
        //如果不想这么用，需要重写比较多的代码
        //这里设定，调用这个接口的都是资源服务
        org.springframework.security.core.userdetails.User clientUser = new org.springframework.security.core.userdetails.User(clientId, clientSecret, new ArrayList<>());

        //生成已经认证的client
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(clientUser, null, new ArrayList<>());

        //封装成一个UserPassword方式的参数体
        Map<String, String> map = new HashMap<>();
        map.put("username", loginDto.getUsername());
        map.put("password", loginDto.getPassword());
        //授权模式为：密码模式
        map.put("grant_type", "password");

        //调用自带的获取token方法。
        OAuth2AccessToken resultToken = tokenEndpoint.postAccessToken(token, map).getBody();

        if (resultToken != null) {
            log.info("获取token成功，进行登录信息的储存");

            // 记录登录ip、登录次数、登录时间
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.and(
                    QueryWrapper -> QueryWrapper
                            .eq(UserSQLConstants.USERNAME, loginDto.getUsername())
                            .or()
                            .eq(UserSQLConstants.EMAIL, loginDto.getUsername())
            );

            // 修改登录信息
            User user = userService.getOne(userQueryWrapper);
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(IpUtils.getIpAddress(httpServletRequest));
            user.setLoginCount(user.getLoginCount() + 1);
            user.setLastLoginAddress(IpUtils.getCityInfo(user.getLastLoginIp()));

            // 更新
            userService.updateById(user);
            log.info("登录信息更新");

            // 记录token到缓存
            redisService.setCacheObject(AuthRedisConstants.USER_TOKEN + ":" + user.getUid(), resultToken.getValue());

            // 移除加密盐
            map.remove(BaseSysConstants.SALT);
        }

        return resultToken;
    }

    /**
     * 登出操作，移除token
     * @param httpServletRequest
     * @return
     */
    public void removeToken(HttpServletRequest httpServletRequest) {
        String authorization = SuperController.getHeaderAuthorization(httpServletRequest);
        String token = "";
        if (!StringUtils.isEmpty(authorization)) {
            token = authorization.replace("Bearer ", "");
        }
        OAuth2AccessToken oAuth2AccessToken = redisTokenStore.readAccessToken(token);
        redisTokenStore.removeAccessToken(oAuth2AccessToken);
        // 移除缓存token
        redisService.deleteObject(AuthRedisConstants.USER_TOKEN + ":" + SuperController.getUserUid(httpServletRequest));
    }

    /**
     * 通过userUid进行登出操作
     * @param userUid
     */
    public void removeToken(String userUid) {
        // 移除缓存token
        String token = redisService.getCacheObject(AuthRedisConstants.USER_TOKEN + ":" + userUid);
        OAuth2AccessToken oAuth2AccessToken = redisTokenStore.readAccessToken(token);
        redisTokenStore.removeAccessToken(oAuth2AccessToken);
        // 移除缓存token
        redisService.deleteObject(AuthRedisConstants.USER_TOKEN + ":" + userUid);
    }


    /**
     * 注册
     * @param registerDto
     * @return
     */
    public Boolean register(RegisterDto registerDto) {
        // 检查邮箱是否已经被使用
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ne(UserSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        userQueryWrapper.eq(UserSQLConstants.EMAIL, registerDto.getEmail());
        int count = userService.count(userQueryWrapper);
        if (count > 0) {
            throw new POauthException(HttpStatus.USER_OR_EMAIL_EXIST);
        }

        String code = redisService.getCacheObject(AuthRedisConstants.EMAIL_CODE + AuthRedisConstants.SEGMENTATION + registerDto.getEmail());

        // 检查验证码
        if (StringUtils.isEmpty(code) || !code.equalsIgnoreCase(registerDto.getCode())) {
            throw new POauthException(HttpStatus.VALIDATION_CODE_INCORRECT);
        }

        UserInfoEditDto userInfoEditDto = new UserInfoEditDto();
        userInfoEditDto.setUsername(registerDto.getUsername());
        userInfoEditDto.setPassword(registerDto.getPassword());
        userInfoEditDto.setStatus(EStatus.ENABLE.getStatus());
        userInfoEditDto.setRoleUid((String) siteSettingService.getByNameEn(SiteSettingConstants.DEFAULT_ROLE_UID_NAME_EN).getValue());
        userInfoEditDto.setNickname(registerDto.getNickname());
        userInfoEditDto.setEmail(registerDto.getEmail());
        userInfoEditDto.setIsEmailActivated(true);

        Boolean save = userService.save(userInfoEditDto);

        if (save) {
            return true;
        }

        throw new POauthException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 忘记密码，重置操作
     * @param editPasswordDto
     * @return
     */
    public Boolean editPassword(EditPasswordDto editPasswordDto) {
        // 检查验证码
        String code = redisService.getCacheObject(AuthRedisConstants.EMAIL_CODE + AuthRedisConstants.SEGMENTATION + editPasswordDto.getEmail());
        // 检查验证码
        if (StringUtils.isEmpty(code) || !code.equalsIgnoreCase(editPasswordDto.getCode())) {
            throw new POauthException(HttpStatus.VALIDATION_CODE_INCORRECT);
        }

        // 查找用户
        PQueryWrapper<User> pQueryWrapper = new PQueryWrapper<>();
        pQueryWrapper.eq(UserSQLConstants.EMAIL, editPasswordDto.getEmail());

        User user = userService.getOne(pQueryWrapper);
        if (user == null) {
            throw new POauthException(HttpStatus.EMAIL_UNACTIVATED);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setPassword(passwordEncoder.encode(editPasswordDto.getPassword() + user.getSalt()));
        boolean res = userService.updateById(user);

        if (res) {
            return true;
        }
        throw new POauthException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 缓存验证码，有效期15分钟
     * @param key
     * @param code
     **/
    public void saveImageCode(String key, String code) {
        redisService.setCacheObject(
                AuthRedisConstants.IMAGE_CODE + AuthRedisConstants.SEGMENTATION + key,
                code,
                15L,
                TimeUnit.MINUTES
        );
    }

    /**
     * 获取验证码拼图（生成的抠图和带抠图阴影的大图及抠图坐标）
     * @param captcha
     * @return
     */
    public ResponseResult getImageCaptcha(CaptchaDto captcha, String ip) {

        //设置画布宽度默认值
        if (captcha.getCanvasWidth() == null) {
            captcha.setCanvasWidth(320);
        }
        //设置画布高度默认值
        if (captcha.getCanvasHeight() == null) {
            captcha.setCanvasHeight(155);
        }
        //设置阻塞块宽度默认值
        if (captcha.getBlockWidth() == null) {
            captcha.setBlockWidth(65);
        }
        //设置阻塞块高度默认值
        if (captcha.getBlockHeight() == null) {
            captcha.setBlockHeight(55);
        }
        //设置阻塞块凹凸半径默认值
        if (captcha.getBlockRadius() == null) {
            captcha.setBlockRadius(9);
        }
        //设置图片来源默认值
        if (captcha.getPlace() == null) {
            captcha.setPlace(0);
        }

        //获取画布的宽高
        int canvasWidth = captcha.getCanvasWidth();
        int canvasHeight = captcha.getCanvasHeight();
        //获取阻塞块的宽高/半径
        int blockWidth = captcha.getBlockWidth();
        int blockHeight = captcha.getBlockHeight();
        int blockRadius = captcha.getBlockRadius();
        //获取资源图
        Map<String, SiteSetting> stringSiteSettingMap = siteSettingService.map();
        SiteSetting captchaUrlSetting = stringSiteSettingMap.get(SiteSettingConstants.CAPTCHA_URL_EN);
        BufferedImage canvasImage = CaptchaUtils.getBufferedImage((String) captchaUrlSetting.getValue());
        //调整原图到指定大小
        canvasImage = CaptchaUtils.imageResize(canvasImage, canvasWidth, canvasHeight);
        //随机生成阻塞块坐标
        int blockX = CaptchaUtils.getNonceByRange(blockWidth, canvasWidth - blockWidth - 10);
        int blockY = CaptchaUtils.getNonceByRange(10, canvasHeight - blockHeight + 1);
        //阻塞块
        BufferedImage blockImage = new BufferedImage(blockWidth, blockHeight, BufferedImage.TYPE_4BYTE_ABGR);
        //新建的图像根据轮廓图颜色赋值，源图生成遮罩
        CaptchaUtils.cutByTemplate(canvasImage, blockImage, blockWidth, blockHeight, blockRadius, blockX, blockY);
        // 移动横坐标
        String nonceStr = StringUtils.getUUID();
        // 缓存， 现在加上ip，防止一个请求使用了来自另一个请求生成的验证码通过的情况
        saveImageCode(nonceStr + ip,String.valueOf(blockX));
        //设置返回参数
        captcha.setNonceStr(nonceStr);
        captcha.setBlockY(blockY);
        captcha.setBlockSrc(CaptchaUtils.toBase64(blockImage, "png"));
        captcha.setCanvasSrc(CaptchaUtils.toBase64(canvasImage, "png"));
        return ResponseResult.success(captcha);
    }
}
