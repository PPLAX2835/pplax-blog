package xyz.pplax.pplaxblog.commons.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * 文件有关的工具类
 */
public class FileUtils {
	
	/**
	 * 上传文件
	 * @param pathRoot 物理路径webapp所在路径  
	 * @return  返回图片上传的地址
	 * @throws IOException 
	 * @throws IllegalStateException
	 * 
	 */
	public static String uploadFile(String pathRoot, String baseUrl, MultipartFile avatar) throws IllegalStateException, IOException {          
        String path="";  
        if(!avatar.isEmpty()){  
            //获得文件类型（可以判断如果不是图片，禁止上传）  
            String contentType=avatar.getContentType();
            if (contentType.indexOf("image") != -1) {
            	 //获得文件后缀名称  
                String imageName = contentType.substring(contentType.indexOf("/")+1);  
                path= baseUrl + StrUtils.getUUID() + "." + imageName;  
                avatar.transferTo(new File(pathRoot + path));
            }          
        }
        return path;
	}
	
	/**
	 * 上传音乐
	 * @param pathRoot
	 * @param baseUrl
	 * @param music
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String uploadMusic(String pathRoot, String baseUrl, MultipartFile music) throws IllegalStateException, IOException {          
        String path="";  
        if(!music.isEmpty()){  
            //获得文件类型（可以判断如果不是图片，禁止上传）  
            String contentType=music.getContentType();
            if (contentType.indexOf("mp3") != -1) {
            	 //获得文件后缀名称  
                String imageName = contentType.substring(contentType.indexOf("/")+1);  
                path= baseUrl + StrUtils.getUUID() + "." + imageName;  
                music.transferTo(new File(pathRoot + path));
            }          
        }
        return path;
	}
}
