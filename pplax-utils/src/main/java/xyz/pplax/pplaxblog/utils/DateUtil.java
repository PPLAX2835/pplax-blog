package xyz.pplax.pplaxblog.utils;
/**
 * 对时间的操作的工具类
 */
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {
	
	public static Logger log = Logger.getLogger(DateUtil.class);
	
	public static final String STARTTIME=" 00:00:00";
	public static final String ENDTIME=" 23:59:59";
	
	private DateUtil() {}
	/**
	 * 获取现在的时间 yyyy-MM-dd HH:mm:ss
	 * @author wangqiang
	 * 2018年5月8日  上午9:28:12
	 * @return
	 */
	public static String getNowTime() {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return formate.format(date);
	}
	
	/**
	 * 
	 * @return
	 * @author 许志翔
	 * @date 2018年6月14日
	 */
	public static String getNowTimeFormat() {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date(System.currentTimeMillis());
		return formate.format(date);
	}
	/**
	 * 获取今天开始的时间
	 * @author wangqiang
	 * 2018年5月8日  上午9:40:53
	 * @return
	 */
	public static String getToDayStartTime() {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date date = new Date(System.currentTimeMillis());
		return formate.format(date);
	}
	
	/**
	 * 获取今天结束的时间
	 * @author 许志翔
	 * 2018年6月12日
	 * @return
	 */
	public static String getToDayEndTime() {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Date date = new Date(System.currentTimeMillis());
		return formate.format(date);
	}
	
	/**
	 * 获取昨天开始的时间
	 * @author 许志翔
	 * 2018年6月11日
	 * @return
	 */
	public static String getYestodayStartTime() {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date date = new Date(System.currentTimeMillis() - 24*60*60*1000L);
		return formate.format(date);
	}
	
	/**
	 * 获取昨天结束的时间
	 * @return
	 * @author 许志翔
	 * @date 2018年6月11日
	 */
	public static String getYestodayEndTime() {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Date date = new Date(System.currentTimeMillis() - 24*60*60*1000L);
		return formate.format(date);
	}
	
	/**
	 * 获取本周开始的时间
	 * @return
	 * @author 许志翔
	 * @date 2018年6月13日
	 */
	public static Date getWeekStartTime() {
		// 获得本周一0点时间
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return  cal.getTime();
	}
	
	/**
	 * 将  String 转换成  date
	 * @param dateTime
	 * @return
	 * @author 许志翔
	 * @date 2018年6月19日
	 */
	public static Date strToDateTime(String dateTime) {
		Date date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = format.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将  date 转换成  时间戳
	 * @param dateTime
	 * @return
	 * @author 许志翔
	 * @date 2018年6月26日
	 */
    public static Long dateToStamp(String s) throws ParseException{

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }
	
	/**
	 * Date 转换成  String
	 * @param dateTime
	 * @return
	 * @author 许志翔
	 * @date 2018年6月19日
	 */
	public static String dateTimeToStr(Date dateTime) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(dateTime);
	}
	
	/**
	 * 获取本周开始的时间的字符串
	 * @return
	 * @author 许志翔
	 * @date 2018年6月13日
	 */
	public static String getWeekStartTimeStr() {
		// 获得本周一0点时间
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		return  formate.format(cal.getTime());
	}
	
	/**
	 * 获取本周结束的时间
	 * @return
	 * @author 许志翔
	 * @date 2018年6月13日
	 */
	public static Date getWeekEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getWeekStartTime());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
	}
	
	/**
	 * 获取本周结束的时间的字符串
	 * @return
	 * @author 许志翔
	 * @date 2018年6月13日
	 */
	public static String getWeekEndTimeStr() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getWeekStartTime());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		return  formate.format(cal.getTime());
	}
	
	/**
	 * 获取上周开始的时间的字符串
	 * @return
	 * @author 许志翔
	 * @date 2018年6月13日
	 */
	public static String getLastWeekStartTimeStr() {
		int weeks = -1;
        int mondayPlus = getMondayPlus();    
        GregorianCalendar currentDate = new GregorianCalendar();    
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);    
        Date monday = currentDate.getTime();    
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return formate.format(monday);
	}
	
	/**
	 * 获取上周结束的时间的字符串
	 * @return
	 * @author 许志翔
	 * @date 2018年6月13日
	 */
	public static String getLastWeekEndTimeStr() {
		int weeks = -1;
        int mondayPlus = getMondayPlus();    
        GregorianCalendar currentDate = new GregorianCalendar();    
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);    
        Date monday = currentDate.getTime();    
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        return formate.format(monday);    
	}
	
	/**
	 * 获取本月开始的时间
	 * @return
	 * @author 许志翔
	 * @date 2018年6月13日
	 */
	public static Date getMonthStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return  cal.getTime();
	}
	
	/**
	 * 获取本月开始的时间的字符串
	 * @return
	 * @author 许志翔
	 * @date 2018年6月13日
	 */
	public static String getMonthStartTimeStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		return  formate.format(cal.getTime());
	}
	
	/**
	 * 获取本月结束的时间
	 * @return
	 * @author 许志翔
	 * @date 2018年6月13日
	 */
	public static Date getMonthEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTime();
	}
	
	/**
	 * 获取本月结束的时间的字符串
	 * @return
	 * @author 许志翔
	 * @date 2018年6月13日
	 */
	public static String getMonthEndTimeStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		return formate.format(cal.getTime());
	}
	
    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    
    /**  
     * 得到二个日期间的间隔天数  
     */    
    public static int getTwoDay(String sj1, String sj2) {    
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");    
        Long day = 0L;    
        try {    
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);    
        } catch (Exception e) {    
            return 0;    
        }    
        return day.intValue();    
    }

    /**
     * 判断某个日期属于本周的第几天 (星期一代表第一天)
     * @param dateTime
     * @return
     * @throws ParseException
     * @author 许志翔
     * @date 2018年6月13日
     */
    public static int getDaysByWeek(String dateTime) throws ParseException {
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = dateFormat.parse(dateTime);
    	cal.setTime(date);
    	int day = cal.get(Calendar.DAY_OF_WEEK);
    	day = day - 1;
    	if(day == 0 ) {
    		day = 7;
    	}
    	return day;
    }
    
    /**
     * 判断某个日期属于本月的第几天
     * @param dateTime
     * @return
     * @throws ParseException
     * @author 许志翔
     * @date 2018年6月14日
     */
    public static int getDaysByMonth(String dateTime) throws ParseException {
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = dateFormat.parse(dateTime);
    	cal.setTime(date);
    	int day = cal.get(Calendar.DAY_OF_MONTH);
    	return day;
    }
    
    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
	
    
	/**
	 * 获取当前的年
	 * @author wangqiang
	 * 2018年5月8日  上午9:36:12
	 * @return
	 */
	public static Integer getYears() {
		Calendar calendar = new GregorianCalendar(TimeZone
	            .getDefault());	
		return calendar.get(Calendar.YEAR);
		
	}
	
	/**
	 * 获取当前的月
	 * @author wangqiang
	 * 2018年5月8日  上午9:37:37
	 * @return
	 */
	public static Integer getMonth() {
		Calendar calendar = new GregorianCalendar(TimeZone
	            .getDefault());	
		return calendar.get(Calendar.MONTH)+1;
		
	}
	/**
	 * 获取当前天
	 * @author wangqiang
	 * 2018年5月8日  上午10:31:37
	 * @return
	 */
	public static Integer getDay() {
		Calendar calendar = new GregorianCalendar(TimeZone
	            .getDefault());	
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * wx支付的过期时间
	 * @param hour
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getTime(double hour) {
		Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
		long time = (long) (System.currentTimeMillis()+hour*60*60*1000l);
		Date date = new Date(time);
		SimpleDateFormat formate = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = formate.format(date);
		return format;	
	}
	
    // 获得当前日期与本周日相差的天数    
    private static int getMondayPlus() {    
        Calendar cd = Calendar.getInstance();    
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......    
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1    
        if (dayOfWeek == 1) {    
            return 0;    
        } else {
            return 1 - dayOfWeek;
        }
    } 
    
	/**
	 * 获取几天之后的日期（wq）
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @param day 加减的天数
	 * @return
	 */
    public static Date getDate(String date,int day) {

    	SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	Calendar cal = Calendar.getInstance();
    	try {
			Date beforeDate =formate.parse(date);
			cal.setTime(beforeDate);
			cal.add(Calendar.DAY_OF_MONTH, day);
			//cal.set(beforeDate.getYear(), beforeDate.getMonth(), beforeDate.getDay()+day, beforeDate.getHours(),beforeDate.getSeconds(), beforeDate.getMinutes());
			Date newDate = cal.getTime();
			return newDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return null;
    }
    /**
     * 吧date转换成字符串
     * @param date
     * @param code 例如  yyyy-MM-dd 00:00:00
     * @return
     */
    public static String formateDate(Date date,String code) {
    	SimpleDateFormat formate = new SimpleDateFormat(code);
		return formate.format(date);
    	
    }
    
}
