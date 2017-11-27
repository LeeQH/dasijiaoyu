package com.lqh.dasi.controller;

import java.security.Security;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lqh.dasi.commen.ListUtils;
import com.lqh.dasi.commen.SecurityAES;
import com.lqh.dasi.commen.URLConstant;
import com.lqh.dasi.pojo.Crawler;
import com.lqh.dasi.pojo.Student;
import com.lqh.dasi.pojo.Teacher;
import com.lqh.dasi.service.CrawlerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 爬虫的控制层
 * @author LiQuanhui
 * @date 2017年11月17日 下午5:07:50
 */
@Controller
@RequestMapping("/crawler")
public class CrawlerController {

	@Autowired
	public CrawlerService ls;
	
	/**
	 * 登录
	 * @author LiQuanhui
	 * @date 2017年11月17日 下午5:08:11
	 * @param teacher 教师类
	 * @return 返回到index.jsp
	 */
	@RequestMapping("/login.action")
	public ModelAndView login(Teacher teacher){
		Crawler crawler=new Crawler();
		ls.login(crawler, teacher, URLConstant.LOGIN_URL);
		Map<String,String> classids=ls.getClassId(crawler, URLConstant.QUERY_CLASS_URL);
		crawler.close();
		ModelAndView mav=new ModelAndView("index.jsp");
		encrypt(teacher);//加密传前端，防止账号密码泄露
		mav.addObject("teacher", teacher);
		mav.addObject("classids",classids);
		return mav;
	}
	
	/**
	 * 查询学生的基本信息
	 * @author LiQuanHui
	 * @date 2017年11月19日 上午12:25:32 
	 * @param teacher 教师类
	 * @return 返回到学生信息表
	 */
	@RequestMapping("/stuInfo.action")
	public ModelAndView queryStuInfo(Teacher teacher){
		decrypt(teacher);//解密获取到账号密码
		Crawler crawler=new Crawler();
		ls.login(crawler, teacher, URLConstant.LOGIN_URL);
		List<List<String>> stuInfo=ls.getStuInfo(crawler, URLConstant.QUERY_STUDENT_INFO_URL+teacher.getClassid()+"&pageSize=150");
		crawler.close();
		JSONArray stuInfoJson=JSONArray.fromObject(stuInfo);
		ModelAndView mav=new ModelAndView("stuInfo.jsp");
		mav.addObject("stuInfo",stuInfoJson);
		mav.addObject("teacher",teacher);
		return mav;
	}
	
	/**
	 * 获取成绩排名
	 * @author LiQuanHui
	 * @date 2017年11月19日 下午4:27:41 
	 * @param teacher 教师类
	 * @return
	 */
	@RequestMapping("/scoreRank.action")
	public ModelAndView getScoreRank(Teacher teacher){
		decrypt(teacher);//解密获取到账号密码
		Crawler crawler=new Crawler();
		ls.login(crawler, teacher, URLConstant.LOGIN_URL);
		List<List<List<String>>> page=ls.getScoreRank(crawler, URLConstant.QUERY_RANK_URL+teacher.getClassid());
		crawler.close();
//		ListUtils.printArrayList(page);
		ModelAndView mav=new ModelAndView("scoreRank.jsp");
		mav.addObject("page",page);
		return mav;
	}
	
	/**
	 * 对teacher的loginId与password进行加密
	 * @author LiQuanhui
	 * @date 2017年11月27日 下午5:56:31
	 * @param teacher
	 */
	private void encrypt(Teacher teacher){
		String loginIdAES=SecurityAES.encrypt(teacher.getLoginId());
		String passwordAES=SecurityAES.encrypt(teacher.getPassword());
		teacher.setLoginId(loginIdAES);
		teacher.setPassword(passwordAES);
	}
	
	/**
	 * 对teacher的loginId与password进行解密
	 * @author LiQuanhui
	 * @date 2017年11月27日 下午5:56:55
	 * @param teacher
	 */
	private void decrypt(Teacher teacher){
		String loginIdAES=SecurityAES.decrypt(teacher.getLoginId());
		String passwordAES=SecurityAES.decrypt(teacher.getPassword());
		teacher.setLoginId(loginIdAES);
		teacher.setPassword(passwordAES);
	}
}
