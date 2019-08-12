package pmq.service;

import java.util.List;

import pmq.pojo.Post;
import pmq.pojo.Target;

public interface IIndexService {
	//String返回错误信息，或者success
	public String addPost(String post);//增加一个职位
	
	public List<Post> getAllPost(String conPost);//获取所有的职位信息
	
	public boolean deletePost(String post);//删除职位
	
	public String updateNum(int id,int numObj,int numSub);//更新题目数量
	
	public boolean findPost(String post);//查询是否存在此职位
	
	//增加自定义的职位指标
	public String addIndex(Target target);
	
	//删除自定义职位指标
	public boolean deleteIndex(Integer id);
	
	//更新自定义职位指标
	public boolean updateIndex(Target target);
	
	//通过职位查询此职位下的所有职位指标
	public List<Target> findIndex(Target index);
}
