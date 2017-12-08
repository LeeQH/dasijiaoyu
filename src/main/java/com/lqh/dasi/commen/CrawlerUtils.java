package com.lqh.dasi.commen;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.lqh.dasi.pojo.Crawler;
/**
 * 爬虫的工具类（post，get，cookie等）
 * @author:LiQuanhui
 * @date:2017年11月17日 下午4:43:36
 */
public class CrawlerUtils {
	public static final String domain="ht.dasijiaoyu.com";
	public static final String path="/dasi";
	
	/**
	 * 模拟post请求，并返回Crawler
	 * @author LiQuanhui
	 * @date 2017年11月17日 下午4:44:37
	 * @param crawler Crawler类
	 * @param param post传递的参数
	 * @param post_url post请求的地址
	 * @return Crawler对象
	 */
	public static Crawler post(Crawler crawler,Map<String,String> param,String post_url){
		HttpPost httpPost=new HttpPost(post_url);
		if(param!=null){
			try {
				httpPost.setHeader("Accept-Encoding", "identity");  
				httpPost.setEntity(new UrlEncodedFormEntity(setPostParam(param),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		try {
			CloseableHttpResponse httpResponse=crawler.getHttpClient().execute(httpPost,crawler.getContext());
			if(httpResponse.getStatusLine().getStatusCode()==200){
				crawler.setHttpResponse(httpResponse);
				crawler.setPass(true);
			}else {
				crawler.setPass(false);
				crawler.setInfo(crawler.getInfo().append("链接不上大思后台，请确认该账号能进入大思后台\\n若大思后台更改过密码请在本系统也更改密码，并重新登录\\n"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return crawler;
	}
	
	/**
	 * 模拟get请求
	 * @author LiQuanhui
	 * @date 2017年11月17日 下午4:48:48
	 * @param crawler Crawler类
	 * @param get_url get请求地址
	 * @return Crawler对象
	 */
	public static Crawler get(Crawler crawler,String get_url){
		HttpGet httpGet=new HttpGet(get_url);
		httpGet.setHeader("Accept-Encoding", "identity"); 
		try {
			CloseableHttpResponse httpResponse=crawler.getHttpClient().execute(httpGet,crawler.getContext());
			if(httpResponse.getStatusLine().getStatusCode()==200){
				crawler.setHttpResponse(httpResponse);
				crawler.setPass(true);
			}else {
				crawler.setPass(false);
				crawler.setInfo(crawler.getInfo().append("链接不上大思后台，请确认该账号现在能否大思后台\\n若大思后台更改过密码请在本系统也更改密码，并重新登录\\n"));			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return crawler;
	}
	
	/**
	 * 设置post的参数
	 * @author LiQuanhui
	 * @date 2017年11月17日 下午4:50:06
	 * @param param
	 * @return NameValuePair的list对象
	 */
	public static List<NameValuePair> setPostParam(Map<String,String> param){
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		for(Map.Entry<String,String> entry:param.entrySet()){
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
        return list;
	}
	
	/**
	 * 通过Jsoup解析crawler对象内的response成document对象
	 * @param crawler crawler对象
	 * @return Document对象
	 */
	public static Document getDocument(Crawler crawler){
		Document doc=null;
		try {
			String html = EntityUtils.toString(crawler.getHttpResponse().getEntity(), "UTF-8");
			doc = Jsoup.parse(html);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	
	
	
//	/**
//	 * 获取cookie
//	 * @author LiQuanhui
//	 * @date 2017年11月17日 下午4:51:30
//	 * @param teacher
//	 * @return
//	 */
//	public static Cookie getCookie(Teacher teacher){
//		BasicClientCookie cookie = new BasicClientCookie(teacher.getCookieName(), teacher.getCookieValue());  
//        cookie.setDomain(domain);  
//        cookie.setPath(path);  
//		return cookie;
//	}
	
}
