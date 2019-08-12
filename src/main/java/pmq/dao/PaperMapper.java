package pmq.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pmq.pojo.Paper;

public interface PaperMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Paper record);

    Paper selectByPrimaryKey(Integer id);

    List<Paper> selectAll();

    int updateByPrimaryKey(Paper record);
    
    //根据测试者id获取测试者的所有题目，形成已完成的试卷
    List<Paper> selectAllByTesterId(Integer testerId);
    
    //根据测试者id获取所有未被批改的主观题
    List<Paper> selectAllByTeacher(Integer testerId);
    
    //根据测试者id修改所有的主观题得分,批量操作
    int updateSubScore(@Param("allScore")Map<Integer,Integer>allScore);
    
    //批量插入试卷
    int insertPapers(@Param("list")List<Paper>papers);
 
}