package pmq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pmq.pojo.Post;
import pmq.pojo.Target;

public interface TargetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Target record);

    Target selectByPrimaryKey(Integer id);

    List<Target> selectAll();

    int updateByPrimaryKey(Target record);
    
    //通过职位和二级指标查询 id,假如有多条相同记录，只获取第一条记录
    int selectByPIS(@Param("post")String post,@Param("index_s")String index_s);
    //查询是否含有某个职位
    int selectByP(String post);
    //更改题目数量
    int updateNum(@Param("id")Integer id,@Param("numObj")Integer numObj,@Param("numSub") Integer numSub);
    //删除此职位下的所有指标
    int deletePost(String post);
    
    //通过职位，一级指标，二级指标，进行模糊查询所有的职位指标id
    List<Integer> selectBYPII(@Param("post")String post,
    		@Param("index_f")String indexF,@Param("index_s")String indexS);
    
    //通过一级指标查询所有 职位指标id(相当于查询一级指标所对应的全部二级指标)
    List<Integer> selectBYF(@Param("index_f")String indexF);
    
    //通过职位查询所有的职位指标id
    List<Integer> selectAllByP(String post);
    
    //通过职位获取所有相对应的职位指标
    List<Target> selectByPost(String post);
    
    //通过id查询职位指标的二级指标
    String selectSById(Integer id);
    
    //通过id查询职位指标的客观题题目数量
    Integer selectObjNumById(Integer id);
  //通过id查询职位指标的主观题题目数量
    Integer selectSubNumById(Integer id);
    //查询所有的职位
    List<String> selectAllPost();
    
    //通过职位查询此职位下的二级指标总量
    Integer selectIndexSNumByPost(String post);
    
    //通过职位获取所有相对应的二级指标
    List<String> selectIndexSByPost(String post);
    
  //模糊条件查询所有的职位
    List<String> selectAllPostBy(Target target);
    
  //模糊条件查询所有的职位指标
    List<Target> selectAllBy(Target target);
    
    //获取所有的ID
    List<Integer> selectAllId();
    
    int updateAllNumSubObj(List<Target> list);//设置此二级指标主观题和客观题的题目数量
    List<Post> selectAllNumObjSub(String post);//通过职位获取此职位下的题库客观题题目数量
}