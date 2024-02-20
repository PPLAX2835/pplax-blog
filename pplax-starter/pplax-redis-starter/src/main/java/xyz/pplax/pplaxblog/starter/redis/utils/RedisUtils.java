package xyz.pplax.pplaxblog.starter.redis.utils;

import xyz.pplax.pplaxblog.starter.redis.constants.BaseRedisConstants;

public class RedisUtils {

    /**
     * 将类名修改为reidis key
     * @param className
     * @return
     */
    public static String getRedisKey(String className) {
        StringBuilder result = new StringBuilder();
        char[] charArray = className.toCharArray();

        // Convert camel case to underscore separated uppercase
        int index = 0;
        for (char c : charArray) {
            if (Character.isUpperCase(c) && index != 0) {
                result.append("_").append(Character.toUpperCase(c));
            } else {
                result.append(Character.toUpperCase(c));
            }
            index++;
        }

        result.append(BaseRedisConstants.SEGMENTATION);

        return result.toString();
    }



    /**
     * 获得expire时间在(0,2)倍范围内浮动的值
     * @return
     */
    public static Long getRandomExpire(Long expire) {
        return Math.round(Math.random() * 2 * expire);
    }
}
