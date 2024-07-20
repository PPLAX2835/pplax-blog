package xyz.pplax.pplaxblog.xo.component.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.commons.constants.SiteSettingConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.CaptchaUtils;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.feign.AuthFeignClient;
import xyz.pplax.pplaxblog.xo.constants.redis.AuthRedisConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.UserSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.CaptchaDto;
import xyz.pplax.pplaxblog.xo.dto.EditPasswordDto;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.dto.RegisterDto;
import xyz.pplax.pplaxblog.xo.dto.edit.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.mapper.UserMapper;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;
import xyz.pplax.pplaxblog.xo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 认证 服务实现类
 */
@Service
public class AuthServiceImpl extends SuperServiceImpl<UserMapper, User> implements AuthService {

    private static final Logger log = LogManager.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SiteSettingService siteSettingService;

    @Autowired
    private AuthFeignClient authFeignClient;

    @Autowired
    private RedisService redisService;

    @Value("${pplax.oauth.client-id:pplax}")
    private String clientId;

    @Value("${pplax.oauth.client-secret:pplax123456}")
    private String clientSecret;

    /**
     * 拼图验证码允许偏差
     **/
    private static Integer ALLOW_DEVIATION = 3;

    /**
     * 登录操作时的获取token
     * @param httpServletRequest
     * @param loginDto
     * @return
     */
    public ResponseResult getToken(HttpServletRequest httpServletRequest, LoginDto loginDto) {
        // 先检查验证码
        ResponseResult responseResult = checkImageCode(loginDto.getNonceStr(), loginDto.getValue());
        if (!Objects.equals(responseResult.getCode(), HttpStatus.OK.getCode())) {
            return responseResult;
        }

        // 请求认证服务器
        Map<String, Object> map = JSONObject.parseObject(
                authFeignClient.getToken(
                        clientId,
                        clientSecret,
                        BaseSysConstants.PASSWORD,
                        loginDto.getUsername(),
                        loginDto.getPassword()
                ),
                new TypeReference<Map<String, Object>>() {
                }
        );

        log.error(map);

        if (!StringUtils.isEmpty((String) map.get("access_token"))) {
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

            // 移除加密盐
            map.remove(BaseSysConstants.SALT);

            // 返回结果
            return ResponseResult.success(map);
        } else {
            log.warn("token未获取到");
            return ResponseResult.error(HttpStatus.TOKEN_GET_FAILED);
        }
    }

    @Override
    public ResponseResult register(RegisterDto registerDto) {
        // 检查邮箱是否已经被使用
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ne(UserSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        userQueryWrapper.eq(UserSQLConstants.EMAIL, registerDto.getEmail());
        int count = userService.count(userQueryWrapper);
        if (count > 0) {
            return ResponseResult.error(HttpStatus.USER_OR_EMAIL_EXIST);
        }


        String code = redisService.getCacheObject(AuthRedisConstants.EMAIL_CODE + AuthRedisConstants.SEGMENTATION + registerDto.getEmail());

        // 检查验证码
        if (StringUtils.isEmpty(code) || !code.equalsIgnoreCase(registerDto.getCode())) {
            return ResponseResult.error(HttpStatus.VALIDATION_CODE_INCORRECT);
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
            return ResponseResult.success();
        }

        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseResult forgetPassword(HttpServletRequest httpServletRequest, EditPasswordDto editPasswordDto) {
        // 检查验证码
        String code = redisService.getCacheObject(AuthRedisConstants.EMAIL_CODE + AuthRedisConstants.SEGMENTATION + editPasswordDto.getEmail());
        // 检查验证码
        if (StringUtils.isEmpty(code) || !code.equalsIgnoreCase(editPasswordDto.getCode())) {
            return ResponseResult.error(HttpStatus.VALIDATION_CODE_INCORRECT);
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ne(UserSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        userQueryWrapper.eq(UserSQLConstants.EMAIL, editPasswordDto.getEmail());

        User user = userService.getOne(userQueryWrapper);
        if (user == null) {
            return ResponseResult.error(HttpStatus.EMAIL_UNACTIVATED);
        }


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setPassword(passwordEncoder.encode(editPasswordDto.getPassword() + user.getSalt()));
        boolean res = userService.updateById(user);

        if (res) {
            return ResponseResult.success();
        }
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改密码
     * @param httpServletRequest
     * @param editPasswordDto
     * @return
     */
    @Override
    public ResponseResult editPassword(HttpServletRequest httpServletRequest, EditPasswordDto editPasswordDto) {
        if (!editPasswordDto.getNewPassword().equals(editPasswordDto.getConfirmPassword())) {
            return new ResponseResult(HttpStatus.INCONSISTENT_PASSWORD_INPUT);
        }
        String accessToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
        JSONObject jsonObject = JSON.parseObject(payloadByBase64);
        String userUid = (String) jsonObject.get("uid");
        User user = userService.getById(userUid);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(editPasswordDto.getNewPassword() + user.getSalt(), user.getPassword())) {

            return ResponseResult.error(HttpStatus.LAST_PASSWORD_NOT_ALLOWED);

        }

        if (passwordEncoder.matches(editPasswordDto.getOldPassword() + user.getSalt(), user.getPassword())) {

            user.setPassword(passwordEncoder.encode(editPasswordDto.getNewPassword() + user.getSalt()));
            boolean res = userService.updateById(user);

            if (res) {
                return ResponseResult.success();
            }
            return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseResult.error(HttpStatus.ERROR_PASSWORD);
    }


    /**
     * 校验验证码
     * @param imageKey
     * @param imageCode
     * @return boolean
     **/
    public ResponseResult checkImageCode(String imageKey, String imageCode) {
        String text = redisService.getCacheObject(AuthRedisConstants.IMAGE_CODE + AuthRedisConstants.SEGMENTATION + imageKey);
        if(org.apache.commons.lang3.StringUtils.isBlank(text)){
            return new ResponseResult(HttpStatus.VALIDATION_CODE_EXPIRED);
        }
        // 根据移动距离判断验证是否成功
        if (Math.abs(Integer.parseInt(text) - Integer.parseInt(imageCode)) > ALLOW_DEVIATION) {
            return new ResponseResult(HttpStatus.VALIDATION_FAIL);
        }
        return ResponseResult.success();
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
    public ResponseResult getImageCaptcha(CaptchaDto captcha) {

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
        // 缓存
        saveImageCode(nonceStr,String.valueOf(blockX));
        //设置返回参数
        captcha.setNonceStr(nonceStr);
        captcha.setBlockY(blockY);
        captcha.setBlockSrc(CaptchaUtils.toBase64(blockImage, "png"));
        captcha.setCanvasSrc(CaptchaUtils.toBase64(canvasImage, "png"));
        return ResponseResult.success(captcha);
    }


}
