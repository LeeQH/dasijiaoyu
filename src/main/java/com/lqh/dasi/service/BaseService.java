package com.lqh.dasi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lqh.dasi.commen.ListUtils;
import com.lqh.dasi.mapper.ClassInfoMapper;
import com.lqh.dasi.mapper.StuInfoMapper;
import com.lqh.dasi.mapper.TeacherInfoMapper;
import com.lqh.dasi.pojo.ClassInfo;
import com.lqh.dasi.pojo.ClassInfoExample;
import com.lqh.dasi.pojo.StuInfo;
import com.lqh.dasi.pojo.StuInfoExample;
import com.lqh.dasi.pojo.TeacherInfo;

@Service
public class BaseService {
	private static Logger logger = Logger.getLogger(BaseService.class);

	@Autowired
	private ClassInfoMapper classInfoMapper;
	
	@Autowired
	private StuInfoMapper stuInfoMapper;
	
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
	
	@Transactional
	public void updateStudents(List<StuInfo> stuWeb){
//		先查数据库，通过stu_id比对，以爬的为标准，库少插库多删，都有的更新
		StuInfoExample example = new StuInfoExample();
		example.createCriteria().andClassIdEqualTo(stuWeb.get(0).getClassId());
		List<StuInfo> stuDB=stuInfoMapper.selectByExample(example);
		//爬虫数据的索引，0表示新增的，1表示更改的,2不理会的
		int[] webIndex=new int[stuWeb.size()];
		//数据库数据的索引，0表示要删的，1表示更新的，2不用理会的
		int[] dbIndex=new int[stuDB.size()];
		
		for(int i=0,websize=stuWeb.size();i<websize;i++){
			for (int j = 0,dbsize=stuDB.size(); j <dbsize ; j++) {
				//爬虫的数据与数据库的数据一一比对
				if(stuWeb.get(i).getStuId().equals(stuDB.get(j).getStuId())){
					dbIndex[j]=2;//爬虫有这数据，不删
					webIndex[i]=2;//数据库有这数据，不增
					if (!stuWeb.get(i).equals(stuDB.get(j))) {//内容有变动
						stuWeb.get(i).setLastDate(stuDB.get(j).getLastDate());//将数据库的最后上线时间移交
						webIndex[i]=1;//将web数据索引设为更改
						dbIndex[j]=1;
						break;
					}
				}
			}
		}
		//新增的list
		List<StuInfo> addList=new ArrayList<StuInfo>();
		//修改的list
		List<StuInfo> updList=new ArrayList<StuInfo>();
		//删除的list
		List<StuInfo> delList=new ArrayList<StuInfo>();
		//不管的list
		List<StuInfo> notList=new ArrayList<StuInfo>();
		for (int i = 0; i < webIndex.length; i++) {
			if(webIndex[i]==1){//修改
				updList.add(stuWeb.get(i));
			}else if (webIndex[i]==0) {//新增
				addList.add(stuWeb.get(i));
			}
		}
		for (int i = 0; i < dbIndex.length; i++) {
			if(dbIndex[i]==0){//删除
				delList.add(stuDB.get(i));
			}else if (dbIndex[i]==2) {//不管
				notList.add(stuDB.get(i));
			}
		}
		System.out.println("------新增"+addList.size()+"-----");
//		ListUtils.printArrayList(addList);
		System.out.println("------删除"+delList.size()+"-----");
//		ListUtils.printArrayList(delList);
		System.out.println("------不管"+notList.size()+"-----");
//		ListUtils.printArrayList(notList);
		System.out.println("------更新"+updList.size()+"-----");
//		ListUtils.printArrayList(updList);
		List aList=null;
		if (delList.size()!=0) {
			int a=stuInfoMapper.deleteBatch(delList);
			logger.info("DAO操作：deleteBatch(delList):"+a);
		}
		if (updList.size()!=0) {
			aList.get(0);
			int a=stuInfoMapper.updateBatch(updList);
			logger.info("DAO操作：updateBatch(updList):"+a);
		}
		if (addList.size()!=0) {
			int a=stuInfoMapper.insertBatch(addList);
			logger.info("DAO操作：insertBatch(addList):"+a);
		}
	}
}
