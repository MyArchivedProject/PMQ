package pmq.dao;

import java.util.List;
import pmq.pojo.Tester;

public interface TesterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tester record);
    
    Tester selectByPrimaryKey(Integer id);

    List<Tester> selectAll();

    int updateByPrimaryKey(Tester record);
    
    //进行各项要求的模糊查询
    List<Tester> selectAllBy(Tester tester);
    //更新选择题总分,题目数量.试卷总分值
    int updateObjScore(Tester tester);
    
    //更新主观题总分objTotal，总分total
    int updateSubScore(Tester tester);
    
    //通过id获取客观题分数
    int selectObjScoreById(Integer id);
    
    //通过id查询测试者职位，通过测试职位获取所有已经批改过的试卷
    List<Tester> selectAllMarked(Integer id);
    
    //把Tester的id传进来，然后通过id获取测试职位，然后通风测试职位查询所有的测试者
    List<Tester> selectAllTester(Tester tester);
}