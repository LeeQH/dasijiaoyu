package com.lqh.dasi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lqh.dasi.commen.SecurityAES;
import com.lqh.dasi.commen.URLConstant;
import com.lqh.dasi.pojo.Crawler;
import com.lqh.dasi.pojo.Teacher;
import com.lqh.dasi.service.CrawlerService;

import net.sf.json.JSONArray;

/**
 * 爬虫的控制层
 * 
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
	 * 
	 * @author LiQuanhui
	 * @date 2017年11月17日 下午5:08:11
	 * @param teacher
	 *            教师类
	 * @return 返回到index.jsp
	 */
	@RequestMapping("/login.action")
	public ModelAndView login(Teacher teacher) {
		ModelAndView mav = new ModelAndView();
		Crawler crawler = new Crawler();
		ls.login(crawler, teacher, URLConstant.LOGIN_URL);
		if (crawler.isPass()) {
			Map<String, String> classids = ls.getClassId(crawler, URLConstant.QUERY_CLASS_URL);
			if (crawler.isPass()) {
				encrypt(teacher);// 加密传前端，防止账号密码泄露
				mav.addObject("teacher", teacher);
				mav.addObject("classids", classids);
				mav.setViewName("index.jsp");
			} else {
				mav.addObject("alertInfo", "登陆失败！\\n1.获取班级列表失败！");
				mav.setViewName("redirect:/login.jsp");
			}
		} else {
			mav.addObject("alertInfo", "登陆失败！请确认:\\n1.账号密码是否正确!\\n2.大思后台是否能正常登录！");
			mav.setViewName("forward:/login.jsp");
		}
		crawler.close();
		return mav;
	}

	/**
	 * 查询学生的基本信息
	 * 
	 * @author LiQuanHui
	 * @date 2017年11月19日 上午12:25:32
	 * @param teacher
	 *            教师类
	 * @return 返回到学生信息表
	 */
	@RequestMapping("/stuInfo.action")
	public ModelAndView queryStuInfo(Teacher teacher) {
		ModelAndView mav = new ModelAndView();
		decrypt(teacher);// 解密获取到账号密码
		Crawler crawler = new Crawler();
		ls.login(crawler, teacher, URLConstant.LOGIN_URL);
		if (crawler.isPass()) {
			List<List<String>> stuInfo = ls.getStuInfo(crawler,
					URLConstant.QUERY_STUDENT_INFO_URL + teacher.getClassid() + "&pageSize=150");
			if (crawler.isPass()) {
				JSONArray stuInfoJson = JSONArray.fromObject(stuInfo);
				mav.addObject("stuInfo", stuInfoJson);
				// mav.setViewName("stuInfo.jsp");
			} else {
				mav.addObject("alertInfo", "获取信息失败！\\n1.请稍后再试！");
				// mav.setViewName("stuInfo.jsp");
			}
		} else {
			mav.addObject("alertInfo", "访问失败！\\n1.登录过期，请重新登录");
			mav.addObject("relogin", "true");
			// mav.setViewName("stuInfo.jsp");
		}
		crawler.close();
		mav.setViewName("stuInfo.jsp");
		return mav;
	}

	/**
	 * 获取成绩排名
	 * 
	 * @author LiQuanHui
	 * @date 2017年11月19日 下午4:27:41
	 * @param teacher
	 *            教师类
	 * @return
	 */
	@RequestMapping("/scoreRank.action")
	public ModelAndView getScoreRank(Teacher teacher) {
		ModelAndView mav = new ModelAndView();
		decrypt(teacher);// 解密获取到账号密码
		Crawler crawler = new Crawler();
		ls.login(crawler, teacher, URLConstant.LOGIN_URL);
		if (crawler.isPass()) {
			List<List<List<String>>> page = ls.getScoreRank(crawler, URLConstant.QUERY_RANK_URL + teacher.getClassid());
			if (crawler.isPass()) {
				mav.addObject("page", page);
				// mav.setViewName("scoreRank.jsp");
			} else {
				mav.addObject("alertInfo", "获取信息失败！\\n1.请稍后再试！");
				// mav.setViewName("scoreRank.jsp");
			}
		} else {
			mav.addObject("alertInfo", "访问失败！\\n1.登录过期，请重新登录");
			mav.addObject("relogin", "true");
			// mav.setViewName("scoreRank.jsp");
		}
		crawler.close();
		mav.setViewName("scoreRank.jsp");
		return mav;
	}

	/**
	 * 对teacher的loginId与password进行加密
	 * 
	 * @author LiQuanhui
	 * @date 2017年11月27日 下午5:56:31
	 * @param teacher
	 */
	private void encrypt(Teacher teacher) {
		String loginIdAES = SecurityAES.encrypt(teacher.getLoginId());
		String passwordAES = SecurityAES.encrypt(teacher.getPassword());
		teacher.setLoginId(loginIdAES);
		teacher.setPassword(passwordAES);
	}

	/**
	 * 对teacher的loginId与password进行解密
	 * 
	 * @author LiQuanhui
	 * @date 2017年11月27日 下午5:56:55
	 * @param teacher
	 */
	private void decrypt(Teacher teacher) {
		String loginIdAES = SecurityAES.decrypt(teacher.getLoginId());
		String passwordAES = SecurityAES.decrypt(teacher.getPassword());
		teacher.setLoginId(loginIdAES);
		teacher.setPassword(passwordAES);
	}
}
