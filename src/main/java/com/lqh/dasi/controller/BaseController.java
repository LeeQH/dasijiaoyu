package com.lqh.dasi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lqh.dasi.pojo.MonthScoreInfo;
import com.lqh.dasi.pojo.StuInfo;
import com.lqh.dasi.service.BaseService;

@Controller
@RequestMapping("/base")
public class BaseController {
	private static Logger logger = Logger.getLogger(BaseController.class);	
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 查询学生信息
	 * @author LiQuanhui
	 * @date 2017年12月12日 下午3:20:40
	 * @param classid
	 * @return
	 */
	@RequestMapping("/queryStuInfo.action")
	public ModelAndView queryStuInfo(String classid){
		ModelAndView mav=new ModelAndView();
		List<StuInfo> stuList=baseService.selectStuInfo(classid);
		if (stuList!=null) {
			mav.addObject("stuList", stuList);
		}else {
			mav.addObject("alertInfo", "无记录");
		}
		mav.setViewName("stuInfo.jsp");
		return mav;
	}

	/**查询月成绩
	 * @author LiQuanhui
	 * @date 2017年12月12日 下午3:20:57
	 * @param classid
	 * @return
	 */
	@RequestMapping("/queryMonthScore.action")
	public ModelAndView queryMonthScore(String classid,String month){
		ModelAndView mav=new ModelAndView();
		List<MonthScoreInfo> monthScoreList=baseService.selectMonthScore(classid,month);
		if (monthScoreList!=null&&monthScoreList.size()!=0) {
			mav.addObject("monthScoreList", monthScoreList);
			List<String> monthList=baseService.selectMonthByClassId(classid);
			if(monthList!=null&&monthList.size()!=0){
				mav.addObject("monthList", monthList);
			}
		}else {
			mav.addObject("alertInfo", "无记录");
		}
		mav.setViewName("scoreRank.jsp");
		return mav;
	}
	
	@RequestMapping("/updateGoal.action")
	@ResponseBody
	public String updateGoal(String stuId,Integer goalScore){
		int a=baseService.updateGoalScore(stuId, goalScore);
		if(a!=0)
			return "success";
		else
			return "fail";
	}
}
