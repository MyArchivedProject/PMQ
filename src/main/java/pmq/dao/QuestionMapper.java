package pmq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pmq.pojo.Question;
import pmq.pojo.Target;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);//把id去掉了（自增）,flag去掉了（默认值为0）
    int insertAll(List<Question>list);//把id去掉了（自增）,flag去掉了（默认值为0）

    Question selectByPrimaryKey(Integer id);

    List<Question> selectAll();

    int updateByPrimaryKey(Question record);//更新题目,职位指标id字段去掉
    
    
    
    List<Question> selectAllInnerJoinTarget();//查询所有的题目，当显示所有的题目时用到
   
    //通过二级指标进行题库查询//联合question和target使用内关联查询
   List<Question> selectAllByIndex(Target target);  //废弃-->超优化版
    List<Question> selectAllByIndexPlus(List<Integer>list); //优化版   
  //通过二级指标进行题库查询//联合question和target使用内关联查询//flag不确定
    List<Question> selectAllByIndexA(@Param(value="target")Target target,@Param(value="flag")int flag);
    List<Question> selectAllByIndexAPlus(@Param(value="list")List<Integer> list,@Param(value="flag")Integer falg);//优化版
  //通过二级指标进行题库查询//联合question和target使用内关联查询//objsub不确定
    List<Question> selectAllByIndexB(@Param(value="target")Target target,@Param(value="objsub")int objsub);
    List<Question> selectAllByIndexBPlus(@Param(value="list")List<Integer> list,@Param(value="objsub")Integer objsub);//优化版
  //通过二级指标进行题库查询//联合question和target使用内关联查询//objsub和flag都不确定
    List<Question> selectAllByIndexC(@Param(value="target")Target target,@Param(value="flag")int flag,@Param(value="objsub")int objsub);
    List<Question> selectAllByIndexCPlus(@Param(value="list")List<Integer> list
    		,@Param(value="flag")Integer flag,@Param(value="objsub")Integer objsub);//优化版
    
    
    
    //通过职位指标和 数量进行客观题查询//不包含必考题//生成测试卷时调用
    List<Question> selectObjByIndex(@Param(value="indexId")int indexId,
    		@Param(value="num")int num);
    //通过职位指标和 数量进行主观题查询//不包含必考题//生成测试卷时调用
    List<Question> selectSubByIndex(@Param(value="indexId")int indexId,
    		@Param(value="num")int num);
    
    //获取所有的必考题//未用到
    List<Question> selectMust();
    
    
    
    //获取所有的题目自定义编码，当上传题目时，需要判断此题目是否已经上传过
    List<String> selectAllCode();
   
    //通过id查询职位指标的客观题题目数量
    Integer selectObjNumByIndexId(Integer indexId);   
  //通过id查询职位指标的主观题题目数量
    Integer selectSubNumByIndexId(Integer indexId);   
    
    //通过联合职位指标表,查询此职位下客观题数目总量
    Integer selectObjSum(String post);
    //通过联合职位指标表,查询此职位下主观题数目总量
    Integer selectSubSum(String post);
    

}