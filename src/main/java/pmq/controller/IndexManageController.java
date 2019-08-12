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

import pmq.dao.QuestionMapper;
import pmq.pojo.Target;
import pmq.service.IIndexService;

@Controller
@RequestMapping(value = "admin/target")
public class IndexManageController {
	@Resource(name = "indexService")
	private IIndexService indexService;
	@Resource(name = "target")
	private Target target;
	
	@Resource(name = "questionMapper")
	private QuestionMapper qDAO;

	// 查询所有职位
	@ResponseBody
	@RequestMapping(value = "getPost")
	public String getpost(String post) {
		// JSONObject json = new JSONObject();// 保存返回客户端的信息
		// json.put("all", indexService.getAllPost());
		return JSON.toJSONString(indexService.getAllPost(post));
	}

	// 查询职位指标//模糊查询
	@ResponseBody
	@RequestMapping(value = "getTarget")
	public String getTarget(String post,String indexF,String indexS) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息
		target.setPost(post);
		target.setIndexF(indexF);
		target.setIndexS(indexS);
		json.put("all", indexService.findIndex(target));
		return json.toJSONString();
	}
	// 删除职位指标
		@ResponseBody
		@RequestMapping(value = "deleteTarget")
		public String deleteTarget(String targetId) {
			JSONObject json = new JSONObject();// 保存返回客户端的信息
			Integer id=Integer.parseInt(targetId);
			if(indexService.deleteIndex(id)){
				json.put("message", "success");
			}else{
				json.put("message", "删除失败");
			}
			return json.toJSONString();
		}

	// 增加职位
	@ResponseBody
	@RequestMapping(value = "addPost")
	public String addPost(String post) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息
		if (post == null) {
			json.put("message", "非法连接");
		} else {
			if (post.length() > 25 || post.length() < 1) {
				json.put("message", "职位名称的字符长度为1到25");
			} else {
				String message = indexService.addPost(post);
				json.put("message", message);
			}
			// json.put("all", indexService.findIndex(post));
		}
		return json.toJSONString();
	}

	// 删除职位
	@ResponseBody
	@RequestMapping(value = "deletePost")
	public String deletePost(String post) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息
		if (post == null) {
			json.put("message", "非法连接");
		} else {
			if (indexService.deletePost(post)) {
				json.put("message", "删除成功");
			} else {
				json.put("message", "删除失败");
			}
		}
		return json.toJSONString();
	}

	// 增加自定义职位指标
	@ResponseBody
	@RequestMapping(value = "addIndex")
	public String addIndex(@Valid Target target, BindingResult result) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息
		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			json.putAll(map);
			return json.toJSONString();
		}
		json.put("message", indexService.addIndex(target));
		return json.toJSONString();
	}

	// 更新题目数量
	@ResponseBody
	@RequestMapping(value = "updateNum")
	public String updateNum(@Valid Target target, BindingResult result) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息
		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			json.putAll(map);
			return json.toJSONString();
		}
		json.put("message", indexService.updateNum(target.getId(), target.getNumObj(), target.getNumSub()));

		return json.toJSONString();
	}

	// 获取此二级指标的主客观题的题目数量//废弃
		/*@ResponseBody
		@RequestMapping(value = "getNum")
		public String getNum(int indexId) {					
			JSONObject json = new JSONObject();// 保存返回客户端的信息
			json.put("subNum", qDAO.selectSubNumByIndexId(indexId));
			json.put("objNum", qDAO.selectObjNumByIndexId(indexId));
			return json.toJSONString();
		}*/
}
