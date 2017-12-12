package com.lqh.dasi.commen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 * @author LiQuanhui
 * @date 2017年11月24日 下午5:23:10
 */
public class DateUtils {
	/**一天的毫秒数*/
	public static final double dayMillis=86400000;
	
	/**
	 * string时间转Data
	 * @author LiQuanhui
	 * @date 2017年11月24日 下午5:23:35
	 * @param time String时间
	 * @param format 时间格式
	 * @return date
	 */
	public static Date stringToDate(String time,String format){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
		Date date=null;
		try {
			date = simpleDateFormat.parse(time);
		} catch (ParseException e) {
			System.out.println("不是日期格式！");
		}
		return date;
	}

	/**
	 * 求日期差
	 * @author LiQuanhui
	 * @date 2017年11月24日 下午5:24:04
	 * @param date1 一个时间
	 * @param date2 另一个时间
	 * @return 日期相差天数
	 */
	public static Integer differentDays(Date date1,Date date2){
		Long time1=date1.getTime();
		Long time2=date2.getTime();
		Long days=null;
		if(time1>time2)
			days=time1-time2;
		else
			days=time2-time1;
		double dayNum=(double)days/86400000;
		return  (int)dayNum;
	}
	
	public static String dateToString(Date date,String format){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
		String strdate = simpleDateFormat.format(date);
		return strdate;
	}
	
}
