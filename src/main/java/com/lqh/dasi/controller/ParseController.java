package com.lqh.dasi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/parse")
public class ParseController {

	@RequestMapping("/post")
	public ModelAndView post(){
		return null;
	}
	
	@RequestMapping("/get")
	public ModelAndView get(){
		return null;
	}
}
