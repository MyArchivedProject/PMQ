package pmq.service;

import java.util.List;

import pmq.pojo.Evaluation;

public interface IEvaluationService {
	//更新等级评价
	public boolean updateEva(Evaluation eva);
	
	/*//只能导入//String返回错误信息或者success
	public List<String> importEva(String url) ;*/
	
	//增加自定义等级评价
	public String addEva(Evaluation eva);
	
	//删除等级评价,只能删除等级评价
	public boolean deleteEva(int id);
	
	//通过条件进行模糊查看等级评价
	public List<Evaluation> findAll(Evaluation eva);
	
}
