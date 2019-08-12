package pmq.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pmq.pojo.Question;
import pmq.pojo.Target;

public interface IQuestionService {
	//通过excel导入题库//返回所有错误信息或者成功信息
	public List<String> addQuestion(MultipartFile file) throws IOException;
	public List<String> addQuestionPlus(MultipartFile file) throws IOException;
	
	//根据id号删除题目
	public boolean deleteQById(List<Integer> all);
	
	//条件查询,实际是通过post indexF  indexS 查询到职位指标id,
	//然后通过indexId，question表格的objsub和flag调用不同的DAO层函数进行查询
	//public List<Question> findAllBy(String post,String indexF,String indexS,Integer objsub,Integer flag);
	public List<Question> findAllBy(Target target,Question ques);
	
	//根据id号更新题目信息
	public boolean updateQById(Question ques);
}
