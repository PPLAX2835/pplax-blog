package xyz.pplax.article.util;

import org.springframework.util.StringUtils;
import xyz.pplax.core.util.DateUtils;

import java.util.Date;

/**
 * 和时间相关的工具类
 */
public class TimeUtils {

    /**
     * 判断传入的时间，是否是一个定时时间
     * @param dateStr
     * @return
     */
    public static boolean isTimingPublishTime(String dateStr) {
        if (!StringUtils.hasLength(dateStr)) {
            return false;
        }
        String parse = DateUtils.parse(dateStr);
        // 判断是否是未来时间
        return new Date(DateUtils.getTime(parse)).after(new Date());
    }
}
