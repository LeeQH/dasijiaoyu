package com.lqh.dasi.commen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static final double dayMillis=86400000;
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

	
	public static String differentDays(Date date1,Date date2){
		Long time1=date1.getTime();
		Long time2=date2.getTime();
		Long days=null;
		if(time1>time2)
			days=time1-time2;
		else
			days=time2-time1;
		double dayNum=(double)days/86400000;
		return  (int)dayNum+"";
	}
}
