package com.lqh.dasi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lqh.dasi.mapper.ClassInfoMapper;
import com.lqh.dasi.mapper.TeacherInfoMapper;
import com.lqh.dasi.pojo.ClassInfo;
import com.lqh.dasi.pojo.ClassInfoExample;
import com.lqh.dasi.pojo.TeacherInfo;

@Service
public class BaseService {
	private static Logger logger = Logger.getLogger(BaseService.class);

	@Autowired
	private ClassInfoMapper classInfoMapper;
	
	/**
	 * 更新班级列表，根据该教师id删除班级列表，再添加
	 * @author LiQuanhui
	 * @date 2017年12月5日 下午6:34:08
	 * @param teacherInfo
	 * @param classids
	 */
	@Transactional
	public void updateClasses(TeacherInfo teacherInfo,Map<String, String> classids){
		ClassInfoExample example=new ClassInfoExample();
		example.createCriteria().andTeacherIdEqualTo(teacherInfo.getId());
		classInfoMapper.deleteByExample(example);
		List<ClassInfo> classes=new ArrayList<ClassInfo>();
		for (Map.Entry<String, String> entry : classids.entrySet()) {  
			ClassInfo classInfo=new ClassInfo();
			classInfo.setClassName(entry.getKey());
			classInfo.setClassId(entry.getValue());
			classInfo.setTeacherId(teacherInfo.getId());
			classes.add(classInfo);
		}  
		int result=classInfoMapper.insertBatch(classes);
		logger.info(teacherInfo.getLoginName()+"批量插入："+result);
	}
	
	
	public void updateClasses(TeacherInfo teacherInfo,String classid){
		
	}
}
