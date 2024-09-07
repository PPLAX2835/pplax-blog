package xyz.pplax.pplaxblog.commons.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 经过测试可行
 * 图片压缩工具类
 */
public class ImageUtils {
    //以下是常量,按照阿里代码开发规范,不允许代码中出现魔法值
    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);
    private static final Integer ZERO = 0;
    private static final Integer ONE_ZERO_TWO_FOUR = 1024;
    private static final Integer NINE_ZERO_ZERO = 900;
    private static final Integer THREE_TWO_SEVEN_FIVE = 3275;
    private static final Integer TWO_ZERO_FOUR_SEVEN = 2047;
    private static final Double ZERO_EIGHT_FIVE = 0.85;
    private static final Double ZERO_SIX = 0.6;
    private static final Double ZERO_FOUR_FOUR = 0.44;
    private static final Double ZERO_FOUR = 0.4;

    /**
     * 根据指定大小压缩图片
     *
     * @param source  源图片
     * @param desRate   压缩为原来的desRate% 0到1之间
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(String source, float desRate) throws IOException {
        byte[] imageBytes = img2Byte(source);
        return compressPicForScale(imageBytes, desRate);
    }

    /**
     * 根据指定大小压缩图片
     * @param desRate   压缩为原来的desRate% 0到1之间
     * @return
     */
    public static byte[] compressPicForScale(BufferedImage bufferedImage, float desRate) throws IOException {
        byte[] imageBytes = bufferedImageToByteArray(bufferedImage);
        return compressPicForScale(imageBytes, desRate);
    }

    /**
     * 根据指定大小压缩图片
     * @param desRate   压缩为原来的desRate% 0到1之间
     * @return
     */
    public static byte[] compressPicForScale(byte[] imageBytes, float desRate) throws IOException {
        if (imageBytes == null || desRate > 1 || desRate <= 0) {
            return imageBytes;
        }
        long desFileSize = (long) (imageBytes.length * desRate);
        if (imageBytes.length <= 0 || imageBytes.length <= desFileSize) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // 使用 desRate 来直接缩放图片
            Thumbnails.of(inputStream)
                    .scale(desRate) // 按照desRate缩放
                    .outputQuality(desRate) // 设定输出质量
                    .toOutputStream(outputStream);

            imageBytes = outputStream.toByteArray();

            logger.info("图片原大小={}kb | 压缩后大小={}kb",
                    srcSize / 1024, imageBytes.length / 1024);
        } catch (Exception e) {
            logger.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }


    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < NINE_ZERO_ZERO) {
            accuracy = ZERO_EIGHT_FIVE;
        } else if (size < TWO_ZERO_FOUR_SEVEN) {
            accuracy = ZERO_SIX;
        } else if (size < THREE_TWO_SEVEN_FIVE) {
            accuracy = ZERO_FOUR_FOUR;
        } else {
            accuracy = ZERO_FOUR;
        }
        return accuracy;
    }


    /**
     * 图片转字节数组
     * @param imghPath 图片地址
     * @return 字节数组
     */
    public static byte[] img2Byte(String imghPath){
        byte[] imageBytes = new byte[0];
        try {
            // 读取图片
            BufferedImage image = ImageIO.read(new File(imghPath));

            // 创建字节输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // 使用ImageIO将BufferedImage编码为byte[]
            ImageIO.write(image, "jpg", baos);

            // 转换为字节数组
            imageBytes = baos.toByteArray();

            // 打印字节数组长度
            System.out.println("Image byte array length: " + imageBytes.length);

            // 可以根据需要处理字节数组，例如存储或传输

            // 关闭字节输出流
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageBytes;
    }

    /**
     * 字节转图片
     * @param imageBytes 字节
     * @param filePath 图片地址
     */
    public static void byte2Img(byte[] imageBytes,String filePath){
        // 假设这是你的字节数组，通常这个数组来自文件、网络或其他源

        // 将字节数组转换为BufferedImage
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

            // 确保image不为null，即字节数组确实是一个图片的有效表示
            if (image != null) {
                // 将BufferedImage保存为文件
                File outputFile = new File(filePath);
                String substring = filePath.substring(filePath.lastIndexOf("." )+1);
                ImageIO.write(image,substring, outputFile);
            } else {
                System.out.println("字节数组不是有效的图片数据");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * BufferedImage 转化为 byte[]
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static byte[] bufferedImageToByteArray(BufferedImage bufferedImage) throws IOException {
        //BufferedImage 转化为 ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, CharacterConstants.FILE_SUFFIX_JPG, byteArrayOutputStream);
        //ByteArrayOutputStream 转化为 byte[]
        return byteArrayOutputStream.toByteArray();
    }


    public static void main(String[] args) {

    }
}