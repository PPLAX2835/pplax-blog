package xyz.pplax.pplaxblog.xo.service.site;

import xyz.pplax.pplaxblog.commons.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;

public interface SiteService {

    ResponseResult getHomeData();

    ResponseResult report(HttpServletRequest httpServletRequest);

    ResponseResult getWebSiteInfo();

}
