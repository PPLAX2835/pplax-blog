package xyz.pplax.pplaxblog.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtils {

    /**
     * 将对象转换为byte数组
     *
     * @param object 需要转换的对象
     * @return 转换后的byte数组
     * @throws IOException 如果序列化失败
     */
    public static byte[] objectToBytes(Object object) throws IOException {
        if (object == null) {
            throw new IllegalArgumentException("The object to be serialized cannot be null.");
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            oos.flush();
            return bos.toByteArray();
        }
    }

    /**
     * 将byte数组转换为对象
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的对象
     * @throws IOException 如果反序列化失败
     * @throws ClassNotFoundException 如果找不到类
     */
    public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException("The byte array to be deserialized cannot be null or empty.");
        }

        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        }
    }

    /**
     * 将byte数组转换为指定类型的对象
     *
     * @param bytes 需要转换的byte数组
     * @param clazz 目标对象的Class类型
     * @param <T> 目标对象的类型
     * @return 转换后的对象
     * @throws IOException 如果反序列化失败
     * @throws ClassNotFoundException 如果找不到类
     */
    public static <T> T bytesToObject(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        Object object = bytesToObject(bytes);
        if (clazz.isInstance(object)) {
            return clazz.cast(object);
        } else {
            throw new ClassCastException("Failed to cast the deserialized object to the specified class type.");
        }
    }
}
