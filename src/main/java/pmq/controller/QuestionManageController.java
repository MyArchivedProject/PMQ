package pmq.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import pmq.pojo.Question;
import pmq.pojo.Target;
import pmq.service.IQuestionService;

@Controller
@RequestMapping(value = "admin/ques")
public class QuestionManageController {
	@Resource(name = "questionService")
	private IQuestionService qService;

	// 通过excel导入向题库里增加题目
	@ResponseBody
	@RequestMapping(value = "addQues")
	// @RequestParam(value = "file", required = false)
	public String addQues( MultipartFile file
			, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();// 保存返回给客户端的信息
		//判断文件大小
		if(file.getSize()==0){
			json.put("message", "无效文件");
			return json.toJSONString();
		}else if(file.getSize()>5242880){
			json.put("message", "文件不能大于5M");
			return json.toJSONString();
		}
		List<String> errors;
		try {		
			errors = qService.addQuestionPlus(file);
			if(errors.size()>1){
				json.put("errors", errors);
			}else{
				json.put("success", errors);
			}	
		}catch (IOException e) {
			// TODO Auto-generated catch block
			json.put("message", "系统错误,请重新上传");
			// e.printStackTrace();
		}
		return json.toJSONString();
	}

	// 删除题目
	@ResponseBody
	@RequestMapping(value = "deleteQues")
	public String deleteQues(String data) {
		JSONObject json = new JSONObject();// 保存返回给客户端的信息
		//response.setHeader("Content-type", "text/html;charset=UTF-8");
		if (null == data || !((data.startsWith("[") && data.endsWith("]")))) {
			json.put("message","输入格式错误");
		} else {
			JSONArray obj = JSONObject.parseArray(data);// 解析可能失败的问题，未进行相关处理

			List<Integer> all = new ArrayList<Integer>();// 传入service的参数
			// 确保传入的参数是数字型（无安全性处理）//对不存的编号未进行排除
			for (int i = 0; i < obj.size(); i++) {
				all.add(Integer.parseInt((String) obj.get(i)));
			}
			if (qService.deleteQById(all)) {
				json.put("message","删除成功");
			} else {
				json.put("message","删除失败");
			}
		}
		return json.toJSONString();
	}
	//向题库里获取题目
	@ResponseBody
	@RequestMapping(value = "getQues")
	public String getQues(Target target,Question ques) {
		//String data = request.getParameter("data");
		//JSONObject json = new JSONObject();// 保存返回给客户端的信息
		//if (null == data || !((data.startsWith("{") && data.endsWith("}")))) {
			//return "{\"message\":\"输入错误\"}";
		//}else{
		/*	JSONObject obj = JSONObject.parseObject(data);
			String post=(String) obj.get("post");
			String indexF=(String) obj.get("indexF");
			String indexS=(String) obj.get("indexS");*/
			if(null==target.getPost() || null==target.getIndexF() ||null==target.getIndexS() 
					|| null==ques.getFlag() || null==ques.getObjsub()){
				System.out.println("QM输入错误");
				return "";
			}else{
				//target.getPost(),target.getIndexF(),target.getIndexS()
				List<Question>all=qService.findAllBy(target,ques);
				return JSONObject.toJSONString(all);
			}						
	}
	
	//更新题目信息
		@ResponseBody
		@RequestMapping(value = "updateQues")
		public String updateQues( Question ques,HttpServletRequest request) {
			
			JSONObject json = new JSONObject();// 保存返回给客户端的信息
			if(this.qService.updateQById(ques)){
				json.put("message", "success");
			}else{
				json.put("message", "更新失败");
			}		
			return json.toJSONString();			
		}
}