package xyz.pplax.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



public class JSONUtils {
    /**
     * 从R返回对象中，解析出key对应的数据
     * @param json
     * @param key
     * @param target
     * @param <T>
     * @return
     */
    public static  <T> T parseObjFromResult(String json, String key, Class<T> target) {
        if (json == null) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(json);
        T t = null;
        try {
            t = JSON.parseObject(jsonObject.getString(key), target);
        } catch (Exception e) {
            LogUtils.logExceptionInfo(e);
        }
        return t;
    }

    public static  <T> T parseObjFromResult(Object obj, String key, Class<T> target) {
        String json = ConvertObjectUtils.jsonToString(obj);
        JSONObject jsonObject = JSON.parseObject(json);
        T t = null;
        try {
            t = JSON.parseObject(jsonObject.getString(key), target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
