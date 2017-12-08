package com.lqh.dasi.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lqh.dasi.commen.CrawlerHandle;
import com.lqh.dasi.commen.ListUtils;
import com.lqh.dasi.commen.SecurityAES;
import com.lqh.dasi.commen.URLConstant;
import com.lqh.dasi.pojo.Crawler;
import com.lqh.dasi.pojo.StuInfo;
import com.lqh.dasi.pojo.TeacherInfo;
import com.lqh.dasi.service.BaseService;

/**
 * 业务爬虫的控制层
 * @author LiQuanhui
 * @date 2017年11月17日 下午5:07:50
 */
@Controller
@RequestMapping("/crawler")
public class CrawlerController {
	private static Logger logger = Logger.getLogger(CrawlerController.class);	
	
	@Autowired
	public BaseService baseService;	
	
	/**
	 * 更新班级列表
	 * @author LiQuanhui
	 * @date 2017年12月5日 下午6:30:32
	 * @param teacherInfo
	 * @return
	 */
	@RequestMapping("/updateClasses.action")
	public ModelAndView updateClasses(TeacherInfo teacherInfo){
		ModelAndView mav=new ModelAndView();
		//保存加密好的账密
		String aesName=teacherInfo.getLoginName();
		String aesPwd=teacherInfo.getLoginPwd();
		//解密
		decrypt(teacherInfo);
		Crawler crawler=new Crawler();
		//验证登录
		CrawlerHandle.loginValidate(crawler, teacherInfo, URLConstant.LOGIN_URL);
		if (crawler.isPass()) {
			//从大思后台获取班级列表
			Map<String, String> classids=CrawlerHandle.getClasses(crawler, URLConstant.QUERY_CLASS_URL);
			if (classids!=null) {
				logger.info(teacherInfo.getLoginName()+"爬虫获取班级列表成功");
				//插入数据库
				baseService.updateClasses(teacherInfo,classids);
				mav.addObject("classids", classids);
				mav.addObject("alertInfo", "更新班级列表完成");
			}else {
				logger.info(teacherInfo.getLoginName()+"爬虫获取班级列表失败");
				mav.addObject("alertInfo", crawler.getInfo().toString());
			}
		} else {
			logger.info(teacherInfo.getLoginName() + "大思官网验证失败");
			mav.addObject("alertInfo", crawler.getInfo().toString());
		}
		//设置加密账密,因为刷新了导航页，所以重新加密
		teacherInfo.setLoginName(aesName);
		teacherInfo.setLoginPwd(aesPwd);
		mav.addObject("teacherInfo", teacherInfo);
		mav.setViewName("index.jsp");
		crawler.close();
		return mav;
	}
	
	/**
	 * 更新学生信息，不含最后上限日期
	 * @author LiQuanhui
	 * @date 2017年12月7日 下午4:34:47
	 * @param teacherInfo
	 * @return
	 */
	@RequestMapping("/UpdateStuInfo.action")
	public ModelAndView UpdateStuInfo(TeacherInfo teacherInfo,String classid) {
		ModelAndView mav = new ModelAndView();
		decrypt(teacherInfo);// 解密获取到账号密码
		Crawler crawler = new Crawler();
		//验证登录
		CrawlerHandle.loginValidate(crawler, teacherInfo, URLConstant.LOGIN_URL);
		if (crawler.isPass()) {
			List<StuInfo> stuInfo = CrawlerHandle.getStuInfo(crawler,
					URLConstant.QUERY_STUDENT_INFO_URL + classid + "&pageSize=150",classid);
			if (stuInfo!=null) {
				logger.info(teacherInfo.getLoginName()+"爬虫学生信息成功，共"+stuInfo.size()+"条");
//				ListUtils.printArrayList(stuInfo);
//				先查数据库，通过stu_id比对，以爬的为标准，库少插库多删，都有的更新
				baseService.updateStudents(stuInfo);
//				JSONArray stuInfoJson = JSONArray.fromObject(stuInfo);
				mav.addObject("alertInfo", "更新学生信息完成");
			} else {
				logger.info(teacherInfo.getLoginName()+"爬虫学生信息失败");
				mav.addObject("alertInfo", crawler.getInfo().toString());
			}
		} else {
			logger.info(teacherInfo.getLoginName() + "大思官网验证失败");
			mav.addObject("alertInfo", crawler.getInfo().toString());
		}
		crawler.close();
		mav.setViewName("index.jsp");
		return mav;
	}
	
	/**
	 * 对teacher的loginId与password进行解密
	 * @author LiQuanhui
	 * @date 2017年11月27日 下午5:56:55
	 * @param teacher
	 */
	private void decrypt(TeacherInfo teacher) {
		String loginIdAES = SecurityAES.decrypt(teacher.getLoginName());
		String passwordAES = SecurityAES.decrypt(teacher.getLoginPwd());
		teacher.setLoginName(loginIdAES);
		teacher.setLoginPwd(passwordAES);
	}
	

//
//	/**
//	 * 查询学生的基本信息
//	 * 
//	 * @author LiQuanHui
//	 * @date 2017年11月19日 上午12:25:32
//	 * @param teacher
//	 *            教师类
//	 * @return 返回到学生信息表
//	 */

//
//	/**
//	 * 获取成绩排名
//	 * 
//	 * @author LiQuanHui
//	 * @date 2017年11月19日 下午4:27:41
//	 * @param teacher
//	 *            教师类
//	 * @return
//	 */
//	@RequestMapping("/scoreRank.action")
//	public ModelAndView getScoreRank(Teacher teacher) {
//		ModelAndView mav = new ModelAndView();
//		decrypt(teacher);// 解密获取到账号密码
//		Crawler crawler = new Crawler();
//		ls.login(crawler, teacher, URLConstant.LOGIN_URL);
//		if (crawler.isPass()) {
//			List<List<List<String>>> page = ls.getScoreRank(crawler, URLConstant.QUERY_RANK_URL + teacher.getClassid());
//			if (crawler.isPass()) {
//				mav.addObject("page", page);
//				// mav.setViewName("scoreRank.jsp");
//			} else {
//				mav.addObject("alertInfo", "获取信息失败！\\n1.请稍后再试！");
//				// mav.setViewName("scoreRank.jsp");
//			}
//		} else {
//			mav.addObject("alertInfo", "访问失败！\\n1.登录过期，请重新登录");
//			mav.addObject("relogin", "true");
//			// mav.setViewName("scoreRank.jsp");
//		}
//		crawler.close();
//		mav.setViewName("scoreRank.jsp");
//		return mav;
//	}

}
