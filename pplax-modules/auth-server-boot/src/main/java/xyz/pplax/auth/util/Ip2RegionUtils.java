package xyz.pplax.auth.util;

import lombok.Data;
import org.lionsoul.ip2region.xdb.Searcher;
import xyz.pplax.core.exception.login.LoginException;
import xyz.pplax.core.util.lambda.AssertUtils;

import java.io.IOException;
import java.io.InputStream;


/**
 * Ip归属地的工具类
 */
public class Ip2RegionUtils {

    @Data
    static class IpInfo {
        private String country;
        private String region;
        private String province;
        private String city;
        private String isp;
    }

    /**
     * 获得ip信息
     * @param ip
     * @return
     * @throws Exception
     */
    public static IpInfo getIpLocationInfo(String ip) throws Exception {
        InputStream stream = Ip2RegionUtils.class.getResourceAsStream("/ip2region.xdb");
        AssertUtils.stateThrow(stream != null, () -> new IOException("没有ip2region.xdb文件"));
        Searcher searcher = Searcher.newWithBuffer(stream.readAllBytes());
        String region = searcher.search(ip);
        String[] ipRegionArr = region.split("\\|");
        if (ipRegionArr.length != 5) {
            throw new LoginException("解析ip信息错误");
        }
        IpInfo ipInfo = new IpInfo();
        ipInfo.setCountry(ipRegionArr[0]);
        ipInfo.setRegion(ipRegionArr[1]);
        ipInfo.setProvince(ipRegionArr[2]);
        ipInfo.setCity(ipRegionArr[3]);
        ipInfo.setIsp(ipRegionArr[4]);
        return ipInfo;
    }

    /**
     * 获得ip归属地
     * @param ip
     * @return
     * @throws Exception
     */
    public static String getIpLocation(String ip) throws Exception {
        StringBuilder builder = new StringBuilder();
        IpInfo ipLocationInfo = getIpLocationInfo(ip);
        if (!"0".equals(ipLocationInfo.getCountry())) {
           builder.append( ipLocationInfo.getCountry());
        }
        if (!"0".equals(ipLocationInfo.getProvince())) {
            builder.append( ipLocationInfo.getProvince());
        }
        if (!"0".equals(ipLocationInfo.getCity())) {
            builder.append( ipLocationInfo.getCity());
        }
        return builder.toString();
    }

}
