package com.lqh.dasi.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lqh.dasi.commen.DateUtils;
import com.lqh.dasi.mapper.ClassInfoMapper;
import com.lqh.dasi.mapper.MonthScoreInfoMapper;
import com.lqh.dasi.mapper.StuInfoMapper;
import com.lqh.dasi.pojo.ClassInfo;
import com.lqh.dasi.pojo.ClassInfoExample;
import com.lqh.dasi.pojo.MonthScoreInfo;
import com.lqh.dasi.pojo.MonthScoreInfoExample;
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

	@Autowired
	private MonthScoreInfoMapper monthScoreInfoMapper;

	/**
	 * 更新班级列表，根据该教师id删除班级列表，再添加
	 * 
	 * @author LiQuanhui
	 * @date 2017年12月5日 下午6:34:08
	 * @param teacherInfo
	 * @param classids
	 */
	@Transactional
	public void updateClasses(TeacherInfo teacherInfo, Map<String, String> classids) {
		ClassInfoExample example = new ClassInfoExample();
		example.createCriteria().andTeacherIdEqualTo(teacherInfo.getId());
		classInfoMapper.deleteByExample(example);
		List<ClassInfo> classes = new ArrayList<ClassInfo>();
		for (Map.Entry<String, String> entry : classids.entrySet()) {
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassName(entry.getKey());
			classInfo.setClassId(entry.getValue());
			classInfo.setTeacherId(teacherInfo.getId());
			classes.add(classInfo);
		}
		int result = classInfoMapper.insertBatch(classes);
		logger.info(teacherInfo.getLoginName() + "批量插入：" + result);
	}

	/**
	 * 将爬虫出来的数据与数据库的数据进行对比，数据库无则增，爬出来无的删数据库，不一样的更新，总之和爬虫出来的数据一致
	 * 
	 * @author LiQuanhui
	 * @date 2017年12月11日 上午11:57:44
	 * @param stuWeb
	 *            爬虫出来的数据
	 */
	@Transactional
	public void updateStudents(List<StuInfo> stuWeb) {
		// 先查数据库，通过stu_id比对，以爬的为标准，库少插库多删，都有的更新
		StuInfoExample example = new StuInfoExample();
		example.createCriteria().andClassIdEqualTo(stuWeb.get(0).getClassId());
		List<StuInfo> stuDB = stuInfoMapper.selectByExample(example);
		// 爬虫数据的索引，0表示新增的，1表示更改的,2不理会的
		int[] webIndex = new int[stuWeb.size()];
		// 数据库数据的索引，0表示要删的，1表示更新的，2不用理会的
		int[] dbIndex = new int[stuDB.size()];

		for (int i = 0, websize = stuWeb.size(); i < websize; i++) {
			for (int j = 0, dbsize = stuDB.size(); j < dbsize; j++) {
				// 爬虫的数据与数据库的数据一一比对
				if (stuWeb.get(i).getStuId().equals(stuDB.get(j).getStuId())) {
					dbIndex[j] = 2;// 爬虫有这数据，不删
					webIndex[i] = 2;// 数据库有这数据，不增
					if (!stuWeb.get(i).equals(stuDB.get(j))) {// 内容有变动
						stuWeb.get(i).setId(stuDB.get(j).getId());// 将数据库的最后上线时间移交
						stuWeb.get(i).setLastDate(stuDB.get(j).getLastDate());// 将数据库的最后上线时间移交
						webIndex[i] = 1;// 将web数据索引设为更改
						// dbIndex[j]=1;//不用设，有webIndex标注要更新即可，设了可使dbIndex[j]=2的为没变化的
						break;
					}
				}
			}
		}
		// 新增的list
		List<StuInfo> addList = new ArrayList<StuInfo>();
		// 修改的list
		List<StuInfo> updList = new ArrayList<StuInfo>();
		// 删除的list
		List<StuInfo> delList = new ArrayList<StuInfo>();
		// 不管的list
		// List<StuInfo> notList=new ArrayList<StuInfo>();
		for (int i = 0; i < webIndex.length; i++) {
			if (webIndex[i] == 1) {// 修改
				updList.add(stuWeb.get(i));
			} else if (webIndex[i] == 0) {// 新增
				addList.add(stuWeb.get(i));
			}
		}
		for (int i = 0; i < dbIndex.length; i++) {
			if (dbIndex[i] == 0) {// 删除
				delList.add(stuDB.get(i));
				// }else if (dbIndex[i]==2) {//不管
				// notList.add(stuDB.get(i));
			}
		}
		// System.out.println("------新增"+addList.size()+"-----");
		// ListUtils.printArrayList(addList);
		// System.out.println("------删除"+delList.size()+"-----");
		// ListUtils.printArrayList(delList);
		// System.out.println("------不管"+notList.size()+"-----");
		// ListUtils.printArrayList(notList);
		// System.out.println("------更新"+updList.size()+"-----");
		// ListUtils.printArrayList(updList);
		if (delList.size() != 0) {
			int a = stuInfoMapper.deleteBatch(delList);
			logger.info("DAO操作：deleteBatch(delList):" + a);
		}
		if (updList.size() != 0) {
			int a = stuInfoMapper.updateBatchStu(updList);
			logger.info("DAO操作：updateBatchStu(updList):" + a);
		}
		if (addList.size() != 0) {
			int a = stuInfoMapper.insertBatch(addList);
			logger.info("DAO操作：insertBatch(addList):" + a);
		}
	}

	/**
	 * 查询学生信息
	 * 
	 * @author LiQuanhui
	 * @date 2017年12月12日 下午2:06:34
	 * @param classid
	 * @return
	 */
	public List<StuInfo> selectStuInfo(String classid) {
		StuInfoExample example = new StuInfoExample();
		example.createCriteria().andClassIdEqualTo(classid);
		example.setOrderByClause("stu_name asc");
		List<StuInfo> stuList = stuInfoMapper.selectByExample(example);
		if (stuList != null && stuList.size() != 0) {
			return stuList;
		} else {
			return null;
		}
	}

	/**
	 * 更新最后上线日期
	 * 
	 * @author LiQuanhui
	 * @date 2017年12月12日 下午2:10:18
	 */
	public void updateLastDate(List<List<String>> dayRank, String classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] stuNames = new String[dayRank.size()];
		for (int i = 0, size = dayRank.size(); i < size; i++) {
			stuNames[i] = dayRank.get(i).get(1);
		}
		map.put("stuNames", stuNames);
		Date now = new Date();
		map.put("lastDate", now);
		map.put("classId", classId);
		int a = stuInfoMapper.updateBatchLastDate(map);
		logger.info("DAO操作：updateBatchLastDate(map):" + a);
	}

	/**
	 * 查询月成绩
	 * 
	 * @author LiQuanhui
	 * @date 2017年12月12日 下午3:20:12
	 * @param classId
	 * @return
	 */
	public List<MonthScoreInfo> selectMonthScore(String classId, String month) {
		MonthScoreInfoExample example = new MonthScoreInfoExample();
		example.createCriteria().andClassIdEqualTo(classId).andMonthEqualTo(month);
		example.setOrderByClause("score desc");
		List<MonthScoreInfo> msList = monthScoreInfoMapper.selectByExample(example);
		if (msList != null && msList.size() != 0) {
			return msList;
		} else {
			return null;
		}
	}

	@Transactional
	public void updateMonthScore(List<List<String>> scoreRank, String classId) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String time = year + (month < 10 ? "0" + month : "" + month);
		logger.info("DAO操作：updateMonthScore()当前时间：" + time);

		MonthScoreInfoExample example = new MonthScoreInfoExample();
		example.createCriteria().andMonthEqualTo(time);
		int count = monthScoreInfoMapper.countByExample(example);
		System.out.println(count);
		if (count == 0) {// 该月未有记录，新增
			calendar.add(Calendar.MONTH, -1);
			int year2 = calendar.get(Calendar.YEAR);
			int month2 = calendar.get(Calendar.MONTH) + 1;
			String time2 = year2 + (month2 < 10 ? "0" + month2 : "" + month2);
			logger.info("DAO操作：updateMonthScore()上个月时间：" + time2);
			// 上个月的数据
			List<MonthScoreInfo> msList = selectMonthScore(classId, time2);
			if (msList!=null&&msList.size()!=0) {
				for (int i = 0, size = msList.size(); i < size; i++) {
					msList.get(i).setMonth(time);
					msList.get(i).setScore(null);
					msList.get(i).setGoalScore(null);
					msList.get(i).setFinishGoal(false);
					for (int j = 0, jsize = scoreRank.size(); j < jsize; j++) {
						if (msList.get(i).getStuName().equals(scoreRank.get(j).get(1))) {
							msList.get(i).setScore(Integer.valueOf(scoreRank.get(j).get(2)));
						}
					}
				}
				int a = monthScoreInfoMapper.insertBatch(msList);
				logger.info("DAO操作：insertBatch(msList)：" + a);
			}
		} else {
			// 这个月的数据
			List<MonthScoreInfo> msList = selectMonthScore(classId, time);

			for (int i = 0, isize = msList.size(); i < isize; i++) {
				for (int j = 0, jsize = scoreRank.size(); j < jsize; j++) {
					if (scoreRank.get(j)!=null&&msList.get(i).getStuName().equals(scoreRank.get(j).get(1))) {
						msList.get(i).setScore(Integer.valueOf(scoreRank.get(j).get(2)));
					}
				}
			}
			int a = monthScoreInfoMapper.updateBatch(msList);
			logger.info("DAO操作：updateBatch(msList)：" + a);
		}
	}

	
	public int updateGoalScore(String stuId,Integer goalScore) {
		MonthScoreInfo monthScoreInfo=new MonthScoreInfo();
		monthScoreInfo.setGoalScore(goalScore);
		monthScoreInfo.setStuId(stuId);
		return monthScoreInfoMapper.updateGoal(monthScoreInfo);
	}
	
	public List<String> selectMonthByClassId(String classId){
		return monthScoreInfoMapper.selectMonthByClassId(classId);
	}
}
