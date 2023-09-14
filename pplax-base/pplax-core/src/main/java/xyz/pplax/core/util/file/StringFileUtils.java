package xyz.pplax.core.util.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件工具类
 */
public class StringFileUtils {

    /**
     * 读取文件内容 二进制应该不行
     * @param stream
     * @return
     * @throws IOException
     */
    public static String getFileContent(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder builder = new StringBuilder();
        String readLine = "";
        while ((readLine = reader.readLine()) != null) {
            builder.append(readLine).append("\n");
        }
        String content = builder.toString();
        reader.close();
        return content;
    }
}
