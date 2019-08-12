package pmq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pmq.pojo.Evaluation;

public interface EvaluationMapper {
    int deleteByPrimaryKey(Integer id);

    //把id字段删掉，把index_id字段删掉//只能增加自定义的等级评价
    int insert(Evaluation record);

    Evaluation selectByPrimaryKey(Integer id);

    //index_id字段被去掉
    List<Evaluation> selectAll();

    int updateByPrimaryKey(Evaluation record);
    
   //此函数被TargetService使用，当职位被增加时,添加对应的等级评价，没有评价内容
    int insertOrigin(@Param("indexId")Integer indexId,@Param("post")String post);
    
    /*//通过Index_id更新数据
    int updateByIndexId(Evaluation record);*/
    
    //根据职位进行查询
    List<Evaluation> selectAllByPost(String post);
    
    //查看所有正在使用的评价，根据职位查询//ITestService调用
    List<Evaluation> selectAllByPostF(String post);
    
    //删除此职位下的所有等级评价
    int deleteByPost(String post);
    
    //查询所有自定义编码，用于防止excel重传//未用到
    List<String> selectAllCode();
    /*//通过职位指标Id删除等级评价//当职位指标被删除时,需要调用
    int deleteByIndexId(Integer indexId);*/
    
  //根据条件进行查询//包含flag
    List<Evaluation> selectAllByEvaFlag(Evaluation eva);
  //根据条件进行查询//不包含flag
    List<Evaluation> selectAllByEva(Evaluation eva);
    
    //通过等级评价名称查找等级评价，看是否存在此等级评价
    int selectEvaByPost(String title);
}