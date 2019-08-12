package pmq.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import pmq.dao.TesterMapper;
import pmq.pojo.Admin;
import pmq.pojo.Tester;
import pmq.service.IPaperService;

@Controller
@RequestMapping(value = "admin/paper")
public class PaperManageController {

	@Resource(name = "paperService")
	private IPaperService paperService;
	
	@Resource(name="testerMapper")
	private TesterMapper testerDAO;
	// 获取所有测试者
	@ResponseBody
	@RequestMapping(value = "getAllTester")
	public String getAllTester(Tester tester) {

		JSONObject json = new JSONObject();

		json.put("all", paperService.getAllTester(tester));
		return json.toJSONString();
	}

	// 根据id号删除测试者//级联删除测试者的试卷
	@ResponseBody
	@RequestMapping(value = "deletePaper")
	public String deletePaper(int testerId) {

		JSONObject json = new JSONObject();

		json.put("all", paperService.deletePaper(testerId));
		return json.toJSONString();
	}

	// 通过测试者id获取测试者卷子的所有题目//获取所有的测试者
	@ResponseBody
	@RequestMapping(value = "getPaper")
	public String getPaper(int testerId) {
		JSONObject json = new JSONObject();
		json.put("tester", paperService.getAllTester(testerId));
		json.put("paper", paperService.getPaper(testerId));
		json.put("me", testerDAO.selectByPrimaryKey(testerId));
		return json.toJSONString();
	}

	// 根据测试者id获取主观题//批改主观题时要用到此api
	@ResponseBody
	@RequestMapping(value = "getUndoPaper")
	public String getUndoPaper(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		// 获取 当管理员打开 批改主观题的界面 时设置的session属性testerId
		Integer testerId = (Integer) request.getSession().getAttribute("testerId");
		if (null != testerId) {
			json.put("all", paperService.getUndoPaper(testerId));
		} else {
			json.put("error", "输入错误");
		}
		return json.toJSONString();
	}

	//提交主观题批改结果//
	@ResponseBody
	@RequestMapping(value = "submitSubPaper")
	public String submitSubPaper(HttpServletRequest request) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息
		// 获取客户端传过来的保存答案的json字符串
		String data = request.getParameter("answers");
		/*暂停使用此方案
		 * Map<Integer, Integer> allScore = new HashMap<Integer, Integer>();// 保存主观题的id和批改者所给的得分
		// 解析json字符串进 allScore集合里
		JSONObject obj = JSONObject.parseObject(data);
		Set<String> allKeys = obj.keySet();
		Iterator<String> iter = allKeys.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			String score = obj.getString(key);
			allScore.put(Integer.parseInt(key), Integer.parseInt(score));
		}*/
		@SuppressWarnings("unchecked")
		List<String>scores=(List<String>) JSONArray.parse(data);
		int testerId = Integer.parseInt(request.getParameter("testerId"));
		String teacher=((Admin)request.getSession().getAttribute("admin")).getName();
		if(teacher==null){
			json.put("message","登录超时，请刷新界面");
		}else{
			// 提交主观题的批改		
			json.put("message", paperService.submitSub(scores, testerId,teacher));
		}	
		return json.toJSONString();
	}
}
