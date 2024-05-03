package xyz.pplax.pplaxblog.xo.service;

import xyz.pplax.pplaxblog.commons.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface SiteService {

    Map<String, Object> getDashboardData();

    ResponseResult getHomeData();

    ResponseResult report(HttpServletRequest httpServletRequest);

    ResponseResult getWebSiteInfo();

}
