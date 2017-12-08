package com.lqh.dasi.mapper;

import com.lqh.dasi.pojo.StuInfo;
import com.lqh.dasi.pojo.StuInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StuInfoMapper {
    int countByExample(StuInfoExample example);

    int deleteByExample(StuInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StuInfo record);

    int insertSelective(StuInfo record);

    List<StuInfo> selectByExample(StuInfoExample example);

    StuInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StuInfo record, @Param("example") StuInfoExample example);

    int updateByExample(@Param("record") StuInfo record, @Param("example") StuInfoExample example);

    int updateByPrimaryKeySelective(StuInfo record);

    int updateByPrimaryKey(StuInfo record);
    
    //批量插入
    int insertBatch(List<StuInfo> record);
    //批量插入
    int deleteBatch(List<StuInfo> record);
    //批量更新
    int updateBatch(List<StuInfo> record);
    
}