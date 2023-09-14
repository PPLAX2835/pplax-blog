package xyz.pplax.starter.util;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 响应工具类
 */
public class PPLAXResponseUtils {

    /**
     * 设置响应头
     * @param response
     */
    public static void setContentType(HttpServletResponse response) {
        //设置响应头
        MediaType applicationJson = new MediaType(MediaType.TEXT_HTML_VALUE);
        response.setContentType(applicationJson.getType());
    }

    public static void write(HttpServletResponse response, String writeData) throws IOException {
        setContentType(response);
        PrintWriter writer = response.getWriter();
        writer.write(writeData);
        writer.flush();
        writer.close();
    }
}
