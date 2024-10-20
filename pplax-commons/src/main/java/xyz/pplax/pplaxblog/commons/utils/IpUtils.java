package xyz.pplax.pplaxblog.commons.utils;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 获取用户访问的ip
 */
@Slf4j
public class IpUtils {

    private static Searcher searcher = null;

    static {
        InputStream dbStream = IpUtils.class.getClassLoader().getResourceAsStream("ip2region.xdb");

        if (dbStream == null) {
            log.error("Failed to load ip2region.xdb from classpath.");
        }

        // 将资源写入临时文件
        File tempFile = null;
        try {
            tempFile = File.createTempFile("ip2region", ".xdb");
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = dbStream.read(buffer)) != -1) {
                    out.write(buffer, 0, length);
                }
            }
            log.info("Temporary ip2region.xdb file created at: " + tempFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to create temporary file for ip2region.xdb: " + e.getMessage());
        }

        String dbPath = tempFile.getAbsolutePath();

        // 1、从 dbPath 中预先加载 VectorIndex 缓存
        byte[] vIndex = new byte[0];
        try {
            vIndex = Searcher.loadVectorIndexFromFile(dbPath);
            log.info("Vector index loaded successfully from: " + dbPath);
        } catch (Exception e) {
            log.error(String.format("Failed to load vector index from `%s`: %s", dbPath, e));
        }

        // 2、使用全局的 vIndex 创建带 VectorIndex 缓存的查询对象
        try {
            searcher = Searcher.newWithVectorIndex(dbPath, vIndex);
            log.info("Searcher with vector index created successfully.");
        } catch (Exception e) {
            log.error(String.format("Failed to create vectorIndex cached searcher with `%s`: %s", dbPath, e));
        }
    }



    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }

        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    /**
     * 根据IP地址获取城市
     * @param ip
     * @return
     */
    public static String getCityInfo(String ip) {
        log.info("Current ip is:" + ip);
        if ("127.0.0.1".equals(ip) || "localhost".equals(ip)) {
            return "本机";
        }
        try {
            // 结果的固定格式 国家|区域|省份|城市|ISP 缺省补0
            return searcher.search(ip);
        } catch (Exception e) {
            log.error(String.format("failed to search(%s): %s\n", ip, e));
        }
        return "未知";
    }

    /**
     * 获取访问设备
     *
     * @param request 请求
     * @return {@link UserAgent} 访问设备
     */
    public static UserAgent getUserAgent(HttpServletRequest request){
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }
}
