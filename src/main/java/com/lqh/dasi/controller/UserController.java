package com.lqh.dasi.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lqh.dasi.commen.CrawlerHandle;
import com.lqh.dasi.commen.SecurityAES;
import com.lqh.dasi.commen.URLConstant;
import com.lqh.dasi.pojo.Crawler;
import com.lqh.dasi.pojo.TeacherInfo;
import com.lqh.dasi.service.UserService;
/**
 * 用户相关操作控制层
 * @author LiQuanhui
 * @date 2017年12月5日 下午6:24:57
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);	
	@Autowired
	public UserService userService;
	
	/**
	 * 用户登录，并从数据库获取班级列表
	 * @author LiQuanhui
	 * @date 2017年12月5日 下午6:25:40
	 * @param teacherInfo
	 * @return
	 */
	@RequestMapping("/login.action")
	public ModelAndView login(TeacherInfo teacherInfo){
		ModelAndView mav=new ModelAndView();
		boolean success=userService.login(teacherInfo);
		if (success) {
			logger.info(teacherInfo.getLoginName() + "登录成功");
			Map<String, String> classids=userService.selectClassids(teacherInfo);
			mav.addObject("classids", classids);
			//加密返回页面
			encrypt(teacherInfo);
			mav.addObject("teacherInfo", teacherInfo);
			mav.setViewName("index.jsp");
		}else {
			logger.info(teacherInfo.getLoginName() + "登录失败，请输入正确账号密码");
			mav.addObject("alertInfo", "登录失败，请输入正确账号密码");
			mav.setViewName("forward:/login.jsp");
		}
		return mav;
	}
	
	/**
	 * 注册
	 * @author LiQuanhui
	 * @date 2017年12月5日 下午6:27:35
	 * @param teacherInfo
	 * @return
	 */
	@RequestMapping("/regist.action")
	public ModelAndView regist(TeacherInfo teacherInfo){
		ModelAndView mav=new ModelAndView();
		Crawler crawler= new Crawler();
		//验证该账号是否能登录大思后台
		CrawlerHandle.loginValidate(crawler, teacherInfo, URLConstant.LOGIN_URL);
		if (crawler.isPass()) {
			int result=userService.regist(teacherInfo);
			switch (result) {
			case 0:
				logger.info(teacherInfo.getLoginName() + "注册成功");
				mav.addObject("alertInfo", "注册成功！\\n请登录");
				break;
			case 1:
				logger.info(teacherInfo.getLoginName() + "注册失败");
				mav.addObject("alertInfo", "注册失败！");
				break;
			case 2:
				logger.info("存在该用户:" + teacherInfo.getLoginName());
				mav.addObject("alertInfo", "注册失败！\\n存在该用户，请直接登录");
				break;
			}
		}else {
			logger.info(teacherInfo.getLoginName() + "大思官网验证失败");
			mav.addObject("alertInfo", crawler.getInfo().toString());
		}
		crawler.close();
		mav.setViewName("forward:/login.jsp");
		return mav;
	}

	
	/**
	 * 对teacher的loginId与password进行加密
	 * @author LiQuanhui
	 * @date 2017年11月27日 下午5:56:31
	 * @param teacher
	 */
	private void encrypt(TeacherInfo teacher) {
		String loginIdAES = SecurityAES.encrypt(teacher.getLoginName());
		String passwordAES = SecurityAES.encrypt(teacher.getLoginPwd());
		teacher.setLoginName(loginIdAES);
		teacher.setLoginPwd(passwordAES);
	}
}
