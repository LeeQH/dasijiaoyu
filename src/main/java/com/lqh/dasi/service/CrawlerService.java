package com.lqh.dasi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.lqh.dasi.commen.CrawlerUtils;
import com.lqh.dasi.commen.DateUtils;
import com.lqh.dasi.pojo.Crawler;
import com.lqh.dasi.pojo.Teacher;


@Service
public class CrawlerService {

	/**
	 * 进行登录操作
	 * @author LiQuanHui
	 * @date 2017年11月19日 下午5:48:14 
	 * @param crawler Crawler对象
	 * @param teacher 含有登录信息
	 * @param login_src 登录的地址
	 * @return Crawler对象
	 */
	public Crawler login(Crawler crawler,Teacher teacher,String login_src){
		Map<String,String> parem=new HashMap<String,String>();
		parem.put("loginId", teacher.getLoginId());
		parem.put("password", teacher.getPassword());
		//发送post请求
		CrawlerUtils.post(crawler, parem, login_src);
	
        return crawler;
	}
	
	/**
	 * 获取班级的classid
	 * @author LiQuanHui
	 * @date 2017年11月19日 下午5:54:27 
	 * @param crawler
	 * @param class_src
	 * @return map,key为中文班级名次，value为classid
	 */
	public Map<String,String> getClassId(Crawler crawler,String class_src){
		Map<String,String> map=new HashMap<String,String>();
		CrawlerUtils.get(crawler, class_src);
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
	 * @return List<List<String>>
	 */
	public List<List<String>> getStuInfo(Crawler crawler,String stuInfo_src){
	
		CrawlerUtils.get(crawler, stuInfo_src);
		Document doc=CrawlerUtils.getDocument(crawler);
		
		List<String> cols=null;
		List<List<String>> rows=new ArrayList<List<String>>();
		
		Element table=doc.select("table#studentList").first();
		Elements trs=table.select("tr");
		Elements tds=null;
		for(int i=0,size=trs.size();i<size;i++){
			tds=trs.get(i).select("td");
			cols=new ArrayList<String>();
			for(int j=4;j<9;j++){
//				System.out.print(tds.get(j).text()+"\t");
				cols.add(tds.get(j).text());
			}
//			System.out.println();
			Date now=new Date();
			Date end=DateUtils.stringToDate(tds.get(8).text(),"yyyy-MM-dd");
			if(end==null)
				cols.add("剩余天数");
			else 
				cols.add(DateUtils.differentDays(now, end));
			rows.add(cols);
		}
		return rows;
	}
	
	
	public List<List<List<String>>> getScoreRank(Crawler crawler,String scoreRank_src){
		CrawlerUtils.get(crawler, scoreRank_src);
		Document doc=CrawlerUtils.getDocument(crawler);
		
		List<String> col=null;
		List<List<String>> row=null;
		List<List<List<String>>> page=null;
		Elements trs=null;
		Elements tds=null;
		Elements tables=null;
		
		tables=doc.select("table");
		page=new ArrayList<List<List<String>>>();
		for(int i=0,taSize=tables.size();i<taSize;i++){
			trs=tables.get(i).select("tr");
			row=new ArrayList<List<String>>();
			for(int j=1,trSize=trs.size();j<trSize;j++){//忽略第一行
				tds=trs.get(j).select("td");
				col=new ArrayList<String>();
				for(int k=0,tdSize=tds.size();k<tdSize;k++){
					col.add(tds.get(k).text());
				}
				row.add(col);
			}
			page.add(row);
		}
		return page;
	}
	

}
