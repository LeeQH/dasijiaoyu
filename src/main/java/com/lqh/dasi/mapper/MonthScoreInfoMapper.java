package com.lqh.dasi.mapper;

import com.lqh.dasi.pojo.MonthScoreInfo;
import com.lqh.dasi.pojo.MonthScoreInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MonthScoreInfoMapper {
    int countByExample(MonthScoreInfoExample example);

    int deleteByExample(MonthScoreInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MonthScoreInfo record);

    int insertSelective(MonthScoreInfo record);

    List<MonthScoreInfo> selectByExample(MonthScoreInfoExample example);

    MonthScoreInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MonthScoreInfo record, @Param("example") MonthScoreInfoExample example);

    int updateByExample(@Param("record") MonthScoreInfo record, @Param("example") MonthScoreInfoExample example);

    int updateByPrimaryKeySelective(MonthScoreInfo record);

    int updateByPrimaryKey(MonthScoreInfo record);
    
    //查询条数
    int insertBatch(List<MonthScoreInfo> record);
    //批量修改
    int updateBatch(List<MonthScoreInfo> record);
    //批量目标
    int updateGoal(MonthScoreInfo record);
    //查询月份
    List<String> selectMonthByClassId(String classId);
}