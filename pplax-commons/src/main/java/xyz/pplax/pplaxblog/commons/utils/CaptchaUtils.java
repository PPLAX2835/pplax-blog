package xyz.pplax.pplaxblog.commons.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;

/**
 * 验证码背景图生成工具
 */
public class CaptchaUtils {
    private static final Logger log = LogManager.getLogger(CaptchaUtils.class);

    /**
     * 获取指定范围内的随机数
     **/
    public static int getNonceByRange(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start + 1) + start;
    }

    /**
     * 获取验证码资源图
     **/
    public static BufferedImage getBufferedImage(String captchaUrl) {
        BufferedImage bufferedImage = null;
        try {
            //获取网络资源图片{
            URL url = new URL(captchaUrl);
            bufferedImage = ImageIO.read(url.openStream());
            if (bufferedImage == null) {
                bufferedImage = getRandomBufferedImage(320, 155);
            }
        } catch (Exception e) {
            log.error("获取拼图资源失败，使用默认图片：" + e.getMessage());

            bufferedImage = getRandomBufferedImage(320, 155);
        }
        return bufferedImage;
    }

    /**
     * 调整图片大小
     **/
    public static BufferedImage imageResize(BufferedImage bufferedImage, int width, int height) {
        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resultImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();
        return resultImage;
    }

    /**
     * 抠图，并生成阻塞块
     **/
    public static void cutByTemplate(BufferedImage canvasImage, BufferedImage blockImage, int blockWidth, int blockHeight, int blockRadius, int blockX, int blockY) {
        BufferedImage waterImage = new BufferedImage(blockWidth, blockHeight, BufferedImage.TYPE_4BYTE_ABGR);
        //阻塞块的轮廓图
        int[][] blockData = getBlockData(blockWidth, blockHeight, blockRadius);
        //创建阻塞块具体形状
        for (int i = 0; i < blockWidth; i++) {
            for (int j = 0; j < blockHeight; j++) {
                try {
                    //原图中对应位置变色处理
                    if (blockData[i][j] == 1) {
                        //背景设置为黑色
                        waterImage.setRGB(i, j, Color.BLACK.getRGB());
                        blockImage.setRGB(i, j, canvasImage.getRGB(blockX + i, blockY + j));
                        //轮廓设置为白色，取带像素和无像素的界点，判断该点是不是临界轮廓点
                        if (blockData[i + 1][j] == 0 || blockData[i][j + 1] == 0 || blockData[i - 1][j] == 0 || blockData[i][j - 1] == 0) {
                            blockImage.setRGB(i, j, Color.WHITE.getRGB());
                            waterImage.setRGB(i, j, Color.WHITE.getRGB());
                        }
                    }
                    //这里把背景设为透明
                    else {
                        blockImage.setRGB(i, j, Color.TRANSLUCENT);
                        waterImage.setRGB(i, j, Color.TRANSLUCENT);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //防止数组下标越界异常
                }
            }
        }
        //在画布上添加阻塞块水印
        addBlockWatermark(canvasImage, waterImage, blockX, blockY);
    }

    /**
     * 构建拼图轮廓轨迹
     **/
    private static int[][] getBlockData(int blockWidth, int blockHeight, int blockRadius) {
        int[][] data = new int[blockWidth][blockHeight];
        double po = Math.pow(blockRadius, 2);
        //随机生成两个圆的坐标，在4个方向上 随机找到2个方向添加凸/凹
        //凸/凹1
        int face1 = RandomUtils.nextInt(0,4);
        //凸/凹2
        int face2;
        //保证两个凸/凹不在同一位置
        do {
            face2 = RandomUtils.nextInt(0,4);
        } while (face1 == face2);
        //获取凸/凹起位置坐标
        int[] circle1 = getCircleCoords(face1, blockWidth, blockHeight, blockRadius);
        int[] circle2 = getCircleCoords(face2, blockWidth, blockHeight, blockRadius);
        //随机凸/凹类型
        int shape = getNonceByRange(0, 1);
        //圆的标准方程 (x-a)²+(y-b)²=r²,标识圆心（a,b）,半径为r的圆
        //计算需要的小图轮廓，用二维数组来表示，二维数组有两张值，0和1，其中0表示没有颜色，1有颜色
        for (int i = 0; i < blockWidth; i++) {
            for (int j = 0; j < blockHeight; j++) {
                data[i][j] = 0;
                //创建中间的方形区域
                if ((i >= blockRadius && i <= blockWidth - blockRadius && j >= blockRadius && j <= blockHeight - blockRadius)) {
                    data[i][j] = 1;
                }
                double d1 = Math.pow(i - Objects.requireNonNull(circle1)[0], 2) + Math.pow(j - circle1[1], 2);
                double d2 = Math.pow(i - Objects.requireNonNull(circle2)[0], 2) + Math.pow(j - circle2[1], 2);
                //创建两个凸/凹
                if (d1 <= po || d2 <= po) {
                    data[i][j] = shape;
                }
            }
        }
        return data;
    }
    /**
     * 根据朝向获取圆心坐标
     */
    private static int[] getCircleCoords(int face, int blockWidth, int blockHeight, int blockRadius) {
        //上
        if (0 == face) {
            return new int[]{blockWidth / 2 - 1, blockRadius};
        }
        //左
        else if (1 == face) {
            return new int[]{blockRadius, blockHeight / 2 - 1};
        }
        //下
        else if (2 == face) {
            return new int[]{blockWidth / 2 - 1, blockHeight - blockRadius - 1};
        }
        //右
        else if (3 == face) {
            return new int[]{blockWidth - blockRadius - 1, blockHeight / 2 - 1};
        }
        return null;
    }
    /**
     * 在画布上添加阻塞块水印
     */
    private static void addBlockWatermark(BufferedImage canvasImage, BufferedImage blockImage, int x, int y) {
        Graphics2D graphics2D = canvasImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.8f));
        graphics2D.drawImage(blockImage, x, y, null);
        graphics2D.dispose();
    }
    /**
     * BufferedImage转BASE64
     */
    public static String toBase64(BufferedImage bufferedImage, String type) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, type, byteArrayOutputStream);
            String base64 = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            return String.format("data:image/%s;base64,%s", type, base64);
        } catch (IOException e) {
            log.error("Image to base64 fail");
            //异常处理
            return null;
        }
    }

    private static BufferedImage getRandomBufferedImage(int width, int height) {
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取Graphics2D对象用于绘制
        Graphics2D g2d = image.createGraphics();

        // 设置背景颜色
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, width, height);

        // 设置绘制内容
        g2d.setColor(Color.lightGray);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("This is a image", 50, 100);

        // 创建随机数生成器
        Random random = new Random();

        // 循环绘制随机图形
        for (int i = 0; i < 6; i++) {
            // 随机生成颜色
            Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            g2d.setColor(randomColor);

            // 随机选择要绘制的图形类型
            int shapeType = random.nextInt(3); // 0-矩形，1-椭圆，2-线条

            // 随机生成图形的位置和大小
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int shapeWidth = random.nextInt(100) + 20;  // 宽度范围为20-120
            int shapeHeight = random.nextInt(100) + 20; // 高度范围为20-120

            switch (shapeType) {
                case 0: // 矩形
                    g2d.fillRect(x, y, shapeWidth, shapeHeight);
                    break;
                case 1: // 椭圆
                    g2d.fillOval(x, y, shapeWidth, shapeHeight);
                    break;
                case 2: // 线条
                    int x2 = random.nextInt(width);
                    int y2 = random.nextInt(height);
                    g2d.drawLine(x, y, x2, y2);
                    break;
            }
        }

        // 释放Graphics2D资源
        g2d.dispose();
        return image;
    }
}
