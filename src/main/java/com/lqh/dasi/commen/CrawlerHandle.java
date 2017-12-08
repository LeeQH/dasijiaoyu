package com.lqh.dasi.commen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lqh.dasi.pojo.Crawler;
import com.lqh.dasi.pojo.StuInfo;
import com.lqh.dasi.pojo.TeacherInfo;

public class CrawlerHandle {
	
	/**
	 * 从大思后台进行登录验证操作
	 * @author LiQuanHui
	 * @date 2017年11月19日 下午5:48:14 
	 * @param crawler Crawler对象
	 * @param teacher 含有登录信息
	 * @param login_src 登录的地址
	 * @return Crawler对象
	 */
	public static Crawler loginValidate(Crawler crawler,TeacherInfo teacherInfo,String login_src){
		Map<String,String> parem=new HashMap<String,String>();
		parem.put("loginId", teacherInfo.getLoginName());
		parem.put("password", teacherInfo.getLoginPwd());
		//发送post请求
		CrawlerUtils.post(crawler, parem, login_src);
        return crawler;
	}
	
	/**
	 * 获取该教师有的班级
	 * @author LiQuanHui
	 * @date 2017年11月19日 下午5:54:27 
	 * @param crawler
	 * @param class_src
	 * @return map；key为中文班级名次，value为classid
	 */
	public static Map<String,String> getClasses(Crawler crawler,String class_src){
		Map<String,String> map=new HashMap<String,String>();
		CrawlerUtils.get(crawler, class_src);
		if(!crawler.isPass())
			return null;
		Document doc=CrawlerUtils.getDocument(crawler);
		Element table=doc.getElementById("MyList");
		Elements trs=table.select("tr");
		Element a=null;
		for(int i=1,size=trs.size();i<size;i++){
			//第2个tr开始到最后，每个tr的第一个td里面的a标签
			//<a href='javascript:updateBookshelf("/dasi","656aa588ce5f45bd908ef70e7a293b22");' unique="" target=""><span>大思英语一（72）班</span></a>
			a=trs.get(i).select("td").get(0).selectFirst("a");
			String classid=a.outerHtml().substring(60, 92);
			map.put(a.text(), classid);
		}
		return map;
	}
	
	
	/**
	 * 获取学生信息
	 * @author LiQuanHui
	 * @date 2017年11月19日 下午5:55:19 
	 * @param crawler Crawler对象
	 * @param stuInfo_src 学生信息
	 * @return List<StuInfo>
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static List<StuInfo> getStuInfo(Crawler crawler,String stuInfo_src,String classid) {
		CrawlerUtils.get(crawler, stuInfo_src);
		if(crawler==null)
			return null;
		
		StuInfo stuInfo=null;
		List<StuInfo> stuList=new ArrayList<StuInfo>();
		Document doc=CrawlerUtils.getDocument(crawler);
		Element table=doc.select("table#studentList").first();
		Elements trs=table.select("tr");
		Elements tds=null;
		
		
		for(int i=1,size=trs.size();i<size;i++){
			stuInfo=new StuInfo();
			tds=trs.get(i).select("td");
//			"<input type=\"checkbox\" name=\"studentList_selector_selectcheckbox\" value=\"9bf11485faa54aeab0e6e0582a17f043\" onclick=\"selectorClick(this,'studentBean.id')\">"
//			stuInfo.setStuId(tds.get(0).outerHtml().substring(73, 105));
			stuInfo.setStuId(tds.get(0).selectFirst("input").attr("value"));
			stuInfo.setStuName(tds.get(4).text());
			stuInfo.setParName(tds.get(5).text());
			stuInfo.setTelNum(tds.get(6).text());
			stuInfo.setStartDate(DateUtils.stringToDate(tds.get(7).text(),"yyyy-MM-dd"));
			stuInfo.setEndDate(DateUtils.stringToDate(tds.get(8).text(),"yyyy-MM-dd"));
			stuInfo.setClassId(classid);
			stuList.add(stuInfo);
		}
		return stuList;
	}
	
////public List<Student> getStuInfo(Crawler crawler,String stuInfo_src){
////
////CrawlerUtils.get(crawler, stuInfo_src);
////Document doc=CrawlerUtils.getDocument(crawler);
////
////List<Student> stuList=new ArrayList<Student>();
////Student student=null;
////Element table=doc.select("table#studentList").first();
////Elements trs=table.select("tr");
////Elements tds=null;
////for(int i=0,size=trs.size();i<size;i++){
////	tds=trs.get(i).select("td");
////	student=new Student();
////	student.setStudentName(tds.get(4).text());
////	student.setParentName(tds.get(5).text());
////	student.setPhoneNum(tds.get(6).text());
////	student.setStartDate(tds.get(7).text());
////	student.setEndDate(tds.get(8).text());
////
////	Date now=new Date();
////	Date end=DateUtils.stringToDate(student.getEndDate(),"yyyy-MM-dd");
////	if(end==null)
////		student.setRemainingDay("剩余天数");
////	else 
////		student.setRemainingDay(DateUtils.differentDays(now, end));
////	stuList.add(student);
////}
////return stuList;
////}	
//
//public List<List<List<String>>> getScoreRank(Crawler crawler,String scoreRank_src){
//CrawlerUtils.get(crawler, scoreRank_src);
//if(crawler==null)
//	return null;
//Document doc=CrawlerUtils.getDocument(crawler);
//
//List<String> col=null;
//List<List<String>> row=null;
//List<List<List<String>>> page=null;
//Elements trs=null;
//Elements tds=null;
//Elements tables=null;
//
//tables=doc.select("table");
//page=new ArrayList<List<List<String>>>();
//for(int i=0,taSize=tables.size();i<taSize;i++){
//	trs=tables.get(i).select("tr");
//	row=new ArrayList<List<String>>();
//	for(int j=1,trSize=trs.size();j<trSize;j++){//忽略第一行
//		tds=trs.get(j).select("td");
//		col=new ArrayList<String>();
//		for(int k=0,tdSize=tds.size();k<tdSize;k++){
//			col.add(tds.get(k).text());
//		}
//		row.add(col);
//	}
//	page.add(row);
//}
//return page;
//}
}
