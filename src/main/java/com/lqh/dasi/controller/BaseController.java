package com.lqh.dasi.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lqh.dasi.commen.CrawlerHandle;
import com.lqh.dasi.commen.SecurityAES;
import com.lqh.dasi.commen.URLConstant;
import com.lqh.dasi.pojo.ClassInfo;
import com.lqh.dasi.pojo.Crawler;
import com.lqh.dasi.pojo.TeacherInfo;
import com.lqh.dasi.service.BaseService;

@Controller
@RequestMapping("/base")
public class BaseController {
	private static Logger logger = Logger.getLogger(BaseController.class);	
	@Autowired
	private BaseService baseService;
	

}
