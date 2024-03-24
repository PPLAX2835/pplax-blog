package xyz.pplax.pplaxblog.xo.service.auth;

import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.CaptchaUtils;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.CaptchaDto;
import xyz.pplax.pplaxblog.xo.dto.EditPasswordDto;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;

/**
 * 认证 服务类
 */
public interface AuthService extends SuperService<User> {

    ResponseResult getToken(HttpServletRequest httpServletRequest, LoginDto loginDto);

    ResponseResult editPassword(HttpServletRequest httpServletRequest, EditPasswordDto editPasswordDto);

    void saveImageCode(String key, String code);

    ResponseResult getCaptcha(CaptchaDto captcha);

    ResponseResult checkImageCode(String imageKey, String imageCode);
}
