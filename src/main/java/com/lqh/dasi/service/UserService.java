package com.lqh.dasi.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lqh.dasi.commen.CrawlerHandle;
import com.lqh.dasi.commen.CrawlerUtils;
import com.lqh.dasi.commen.URLConstant;
import com.lqh.dasi.controller.UserController;
import com.lqh.dasi.mapper.ClassInfoMapper;
import com.lqh.dasi.mapper.TeacherInfoMapper;
import com.lqh.dasi.pojo.ClassInfo;
import com.lqh.dasi.pojo.ClassInfoExample;
import com.lqh.dasi.pojo.Crawler;
import com.lqh.dasi.pojo.TeacherInfo;
import com.lqh.dasi.pojo.TeacherInfoExample;

@Service
public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private TeacherInfoMapper teacherInfoMapper;

	@Autowired
	private ClassInfoMapper classInfoMapper;
	
	/**
	 * 进行登录，并获取教师的id
	 * @author LiQuanhui
	 * @date 2017年12月5日 下午6:29:04
	 * @param teacherInfo
	 * @return
	 */
	public boolean login(TeacherInfo teacherInfo) {
		TeacherInfoExample example = new TeacherInfoExample();
		example.createCriteria().andLoginNameEqualTo(teacherInfo.getLoginName())
				.andLoginPwdEqualTo(teacherInfo.getLoginPwd());
		List<TeacherInfo> list = teacherInfoMapper.selectByExample(example);
		if (list != null && list.size() != 0) {
			teacherInfo.setId(list.get(0).getId());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加教师进数据库
	 * @author LiQuanhui
	 * @date 2017年12月4日 下午5:54:45
	 * @param crawler
	 * @param teacherInfo
	 * @return 0注册成功；1注册失败；2存在该用户
	 */
	public int regist(TeacherInfo teacherInfo) {
		TeacherInfoExample example = new TeacherInfoExample();
		example.createCriteria().andLoginNameEqualTo(teacherInfo.getLoginName());
		// 查询是否已存在该用户名
		int count = teacherInfoMapper.countByExample(example);
		if (count == 0) {
			// 不存在则插入
			int result = teacherInfoMapper.insertSelective(teacherInfo);
			if (result != 0) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return 2;
		}
	}

	/**
	 * 从数据库查出班级裂变
	 * @author LiQuanhui
	 * @date 2017年12月7日 下午4:29:26
	 * @param teacherInfo
	 * @return key为班级中文名，value为classid
	 */
	public Map<String, String> selectClassids(TeacherInfo teacherInfo){
		ClassInfoExample example=new ClassInfoExample();
		example.createCriteria().andTeacherIdEqualTo(teacherInfo.getId());
		List<ClassInfo> classes=classInfoMapper.selectByExample(example);
		if (classes!=null&&classes.size()!=0) {
			Map<String,String> classids=new HashMap<String, String>();
			for (int i = 0,size=classes.size(); i < size; i++) {
				classids.put(classes.get(i).getClassName(), classes.get(i).getClassId());
			}
			return classids;
		}else {
			return null;
		}
	}
}
