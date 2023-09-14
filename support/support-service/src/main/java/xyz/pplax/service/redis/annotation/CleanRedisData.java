package xyz.pplax.service.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 清除redis中的数据
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CleanRedisData {
	public enum TYPE{KEY, HASH};
	/** 
	 * REDIS类型，目前只支持key和hash的存放方式
	 */
	TYPE type() default TYPE.HASH;
	
	/**
	 * key
	 */
	String key() default "";

	/**
	 * 其他的key值，比如可以根据key清除字段，也可以根据这个otherKey来清除字段
	 * @return
	 */
	String[] otherKey() default {};
}
