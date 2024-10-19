package xyz.pplax.pplaxblog.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.pplax.pplaxblog.commons.constants.SiteSettingConstants;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 风格 Controller，用来获取先前配置的如背景图片等文件地址
 */
@Controller
@RequestMapping("/style")
@Api(value="风格 Controller", tags={"风格 Controller"})
public class StyleController extends SuperController {

    @Autowired
    private SiteSettingService siteSettingService;

    private static Logger log = LogManager.getLogger(StyleController.class);


    @ApiOperation(value="重定向至logo", notes="重定向至logo")
    @RequestMapping("/logo")
    public void redirectLogo(HttpServletResponse response) throws IOException {

        Map<String, SiteSetting> siteSettingMap = siteSettingService.map();
        SiteSetting siteSetting = siteSettingMap.get(SiteSettingConstants.SITE_LOGO_MANE_EN);
        if (siteSetting == null) {
            return;
        }
        String redirectUrl = ((String) siteSetting.getValue()).trim(); // 确保URL没有空格或特殊字符

        doResponse(response, redirectUrl);
    }

    @ApiOperation(value="重定向至背景", notes="重定向至背景")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "类型",defaultValue = "",paramType = "path",dataType="String",required = true)
    })
    @RequestMapping("/background/{type}")
    public void redirectBackground(
            HttpServletResponse response,
            @PathVariable("type") String type
    ) throws IOException {

        Map<String, SiteSetting> siteSettingMap = siteSettingService.map();
        SiteSetting siteSetting = siteSettingMap.get(type);
        if (siteSetting == null) {
            return;
        }
        String redirectUrl = ((String) siteSetting.getValue()).trim(); // 确保URL没有空格或特殊字符

        doResponse(response, redirectUrl);
    }

    @ApiOperation(value="重定向至主题背景", notes="重定向至主题背景")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "类型",defaultValue = "",paramType = "path",dataType="String",required = true)
    })
    @RequestMapping("/theme/{type}")
    public void redirectTheme(HttpServletResponse response, @PathVariable("type") String type) throws IOException {

        Map<String, SiteSetting> siteSettingMap = siteSettingService.map();
        SiteSetting siteSetting = siteSettingMap.get(SiteSettingConstants.THEME_EN);
        JSONObject valueJsonObj = JSON.parseObject(siteSetting.getValue().toString());            // 获取value

        JSONObject currentThemeJsonObj = (JSONObject) valueJsonObj.get(SiteSettingConstants.CURRENT_THEME);// 获取当前主题
        if ("background".equals(type)) {
            String background = (String) currentThemeJsonObj.get(SiteSettingConstants.BACKGROUND);
            String redirectUrl = background.trim(); // 确保URL没有空格或特殊字符
            doResponse(response, redirectUrl);
        } else if ("effectParticle".equals(type)) {
            String effectParticle = (String) currentThemeJsonObj.get(SiteSettingConstants.EFFECTS_PARTICLE);
            String redirectUrl = effectParticle.trim(); // 确保URL没有空格或特殊字符
            doResponse(response, redirectUrl);
        } else if ("particleJsParam".equals(type)) {
            String effectParticleName = (String) currentThemeJsonObj.get(SiteSettingConstants.PARTICLE_JS_PARAM_NAME);  // 获取当前特效名
            JSONObject themesJsonObj = (JSONObject) valueJsonObj.get(SiteSettingConstants.THEMES); // 获取themes的map
            JSONObject particleJsParamsJsonObj = (JSONObject) themesJsonObj.get(SiteSettingConstants.PARTICLE_JS_PARAMS);// 获取particleJsParams的map
            String particleJsParam = JSON.toJSONString(particleJsParamsJsonObj.get(effectParticleName));        // 获得最后的结果

            String res = String.format("particlesJS('particles-effect', %s);", particleJsParam);
            response.setContentType("text/javascript");
            response.getOutputStream().write(res.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * 进行重定向操作
     * @param response
     * @param redirectUrl
     * @throws IOException
     */
    private void doResponse(HttpServletResponse response, String redirectUrl) throws IOException {

        // Log the redirection URL for debugging purposes
        log.info("Redirecting to URL: " + redirectUrl);

        // Ensure that the URL is absolute and not relative
        if (redirectUrl.startsWith("http://") || redirectUrl.startsWith("https://")) {
            response.sendRedirect(redirectUrl);
        } else {
            log.error("Invalid URL: " + redirectUrl);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid redirect URL");
        }

    }
}
