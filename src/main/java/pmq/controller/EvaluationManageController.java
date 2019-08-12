package pmq.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import pmq.dao.TargetMapper;
import pmq.pojo.Evaluation;
import pmq.service.IEvaluationService;

@Controller
@RequestMapping(value = "admin/eva")
public class EvaluationManageController {
	@Resource(name = "evaluation")
	private Evaluation eva;
	
	@Resource(name = "evaluationService")
	private IEvaluationService evaService;
	
	//当查询某职位下的等级评价的具体信息时,需要获取此职位的所有二级指标,由于方法过于简单,直接使用dao获取
	//一下只有一个API用到此targetDAO
	@Resource(name="targetMapper")
	private TargetMapper targetDAO;
	/*// 通过excel导入，更新原始等级评价等级评价//错误有待修正
	@ResponseBody
	@RequestMapping(value = "updateOriEva")
	public String updateEva(String url) {
		JSONObject json = new JSONObject();// 保存返回给客户端的信息
		if (null == url) {
			json.put("message", "输入错误");
		} else {
			List<String> errors = evaService.importEva(url);
			json.put("message", errors);
		}
		return json.toJSONString();
	}*/
	
	// 增加自定义等级评价
	@ResponseBody
	@RequestMapping(value = "addEva")
	// 未做服务端错误验证//multi_index需要在客户端合成
	public String addEva(@Valid Evaluation eva, BindingResult result) {
		JSONObject json = new JSONObject();// 保存返回给客户端的信息
		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			json.putAll(map);
			return json.toJSONString();
		}
		json.put("message", evaService.addEva(eva));	
		return json.toJSONString();
	}

	// 查询所有等级评价
	@ResponseBody
	@RequestMapping(value = "getEva")
	public String getEva(String post,int flag,String title) {
		JSONObject json = new JSONObject();// 保存返回给客户端的信息
		if (null == post || null==title) {
			json.put("message", "输入错误_1");
		} else {
			eva.setFlag(flag);
			eva.setTitle(title);
			eva.setPost(post);
			json.put("all", evaService.findAll(eva));
		}
		return json.toJSONString();
	}

	// 更新等级评价
	@ResponseBody
	@RequestMapping(value = "updateEva")
	// 未做服务端错误验证//multi_index需要在客户端合成
	public String updateEva(@Valid Evaluation eva, BindingResult result) {
		JSONObject json = new JSONObject();// 保存返回给客户端的信息
		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			json.putAll(map);
			return json.toJSONString();
		}
		if (evaService.updateEva(eva)) {
			json.put("message", "success");
		} else {
			json.put("message", "更新等级评价失败");
		}
		return json.toJSONString();
	}
	//删除等级评价
	@ResponseBody
	@RequestMapping(value = "deleteEva")
	public String deleteEva(String evaId) {
		Integer id=Integer.parseInt(evaId);
		JSONObject json = new JSONObject();
		if(evaService.deleteEva(id)){
			json.put("message", "success");
		}else{
			json.put("message", "删除失败");
		}
		return JSON.toJSONString(json);		
	}
	//当查看等级评价详情或者修改自定义等级评价时,获取此职位对应的所有职位指标信息
	@ResponseBody
	@RequestMapping(value = "getIndexS")
	public String getIndexS(String post) {	
		return JSON.toJSONString(targetDAO.selectByPost(post));
	}
}
