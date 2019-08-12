package pmq.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import pmq.dao.PaperMapper;
import pmq.pojo.Paper;
import pmq.pojo.Tester;
import pmq.pojo.ToTesterEva;
import pmq.service.IIndexService;
import pmq.service.ITestService;

@Controller
@RequestMapping(value = "front")
public class FrontController {
	@Resource(name = "testService")
	private ITestService testService;
	@Resource(name = "indexService")
	private IIndexService indexService;

	@Resource(name = "paperMapper")
	private PaperMapper paperDAO;

	// public static final Logger logger =
	// LogManager.getLogger(FrontController.class.getName());

	// 填写基本信息界面
	@RequestMapping(value = "register")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("front/register");
	}

	// 返回测试完成后的报告界面
	@RequestMapping(value = "report")
	public ModelAndView report(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("front/report");
	}

	// 测评说明界面
	@ResponseBody
	@RequestMapping(value = "instruction")
	public ModelAndView instruction(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("front/instruction");
	}

	// 正式测评界面
	@ResponseBody
	@RequestMapping(value = "test")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("front/test");
	}

	// 获取所有的职位名称
	@RequestMapping(value = "getPost")
	@ResponseBody
	public String getPost() {
		return JSON.toJSONString(testService.getAllPost());
	}

	// 跳入测试界面前的一个中转站
	@ResponseBody
	@RequestMapping(value = "into")
	public String into(HttpServletRequest request, HttpServletResponse response, @Valid Tester tester,
			BindingResult result) {
		JSONObject json = new JSONObject();
		// 输入不符合格式
		if (result.hasErrors()) {
			json.put("flag", "0");
		} else {
			// 保存测试者信息
			if (tester != null && tester.getPost() != null) {
				request.getSession().setAttribute("tester", tester);
				// 此属性作为一个标记，从report后退到head界面
				request.getSession().setAttribute("flag", "1");
				// 此flag作为一个标记，向客户端表示成功
				json.put("flag", "1");
				// 假如是重新测试则移除保存在session里的给 测试者的试卷 和 保存有答案的试卷
				request.getSession().removeAttribute("toTesterPaper");
				request.getSession().removeAttribute("paper");
			} else {
				System.out.println("error:FrontController;head");
				json.put("flag", "0");
			}
		}
		return json.toJSONString();

	}
	//跳入测试主页面 
	@RequestMapping(value = "head")
	public ModelAndView head(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav=new ModelAndView();
		if (request.getSession().getAttribute("flag") == null
				|| request.getSession().getAttribute("flag").equals("0")) {
			mav.setViewName("redirect:register");
		}else{
			Tester tester = (Tester) request.getSession().getAttribute("tester");
			if (tester != null && tester.getPost() != null) {
				mav.setViewName("front/head");
			} else {
				mav.setViewName("redirect:register");
			}
		}
		return mav;
	}

	// 提交试卷后,返回分数信息和等级评价信息
	@ResponseBody
	@RequestMapping(value = "getReport")
	public String getReport(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		@SuppressWarnings("unchecked")
		List<ToTesterEva> report = (List<ToTesterEva>) request.getSession().getAttribute("report");
		Tester tester = (Tester) request.getSession().getAttribute("tester");

		if (null != report && tester != null) {
			json.put("report", report);
			json.put("tester", tester);
		} else {
			json.put("error", "error");
		}
		return json.toJSONString();
	}

	// 刷新保存答案
	@RequestMapping(value = "saveAnswers")
	public void saveAnswers(HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("answers");
		// 防止非法连接//应该进行正则表达式验证
		/*
		 * if (null == data || !((data.startsWith("[") && data.endsWith("]"))))
		 * { System.out.println("!!" + data); }
		 */
		// 存储测试者传过来的答案
		// List<Object> answers = JSONObject.parseArray(data);
		request.getSession().setAttribute("answersDone", data);
	}

	// 测试者获取试卷
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getPaper")
	@ResponseBody
	public String getPaper(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		// 是否是刷新//不是刷新则连接数据库重新获取数据
		if (request.getSession().getAttribute("toTesterPaper") == null) {
			Tester tester = (Tester) request.getSession().getAttribute("tester");
			if (tester != null && null != tester.getPost()) {
				List<List<Paper>> both = testService.getPaper(tester.getPost());
				// 保存session里的试卷
				request.getSession().setAttribute("paper", both.get(0));//完整的
				request.getSession().setAttribute("toTesterPaper", both.get(1));//只含有题目的
				// 返回给测试者的试卷
				json.put("all", both.get(1));
			} else {
				System.out.println("请先进行基本信息的填写");
			}
		} else {
			json.put("all", (List<Paper>) request.getSession().getAttribute("toTesterPaper"));
			// 用户刷新界面,则返回用户之前所答题的答案
			if (null != request.getSession().getAttribute("answersDone")) {
				// @SuppressWarnings("unchecked")
				// List<Object>
				// answersDone=(List<Object>)request.getSession().getAttribute("answersDone");
				json.put("answersDone", request.getSession().getAttribute("answersDone"));
			}
		}

		return json.toJSONString();
	}

	// 测试者提交试卷
	@RequestMapping(value = "submitPaper")
	@ResponseBody
	public String submitPaper(HttpServletRequest request) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息
		// 获取客户端传过来的json字符串
		String data = request.getParameter("answers");
		// 防止非法连接//应该进行正则表达式验证
		if (null == data || !((data.startsWith("[") && data.endsWith("]")))) {
			System.out.println(data);
		}
		List<Object> answers = JSONObject.parseArray(data);

		// 取出保存在session里的含有答案的试卷
		@SuppressWarnings("unchecked")
		List<Paper> papers = (List<Paper>) request.getSession().getAttribute("paper");

		Tester tester = (Tester) request.getSession().getAttribute("tester");

		if (null != papers && null != tester) {
			if (papers.size() == answers.size()) {
				// 批改试卷
				List<ToTesterEva> both = testService.submitPaper(papers, answers, tester);
				if (null == both) {
					json.put("error", "1系统错误,请刷新后重试");
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("tester", tester);
					session.setAttribute("report", both);
					session.removeAttribute("paper");
					session.removeAttribute("answers");
					session.removeAttribute("toTesterPaper");
					session.setAttribute("flag", "0");
					json.put("message", "success");
				}
			} else {
				json.put("error", "2系统错误,请刷新后重试");
				System.out.println("FrontController.java-----题目与答案数量不相等");
			}
		} else {
			json.put("error", "3测试超时,请刷新后重试");
			System.out.println("FrontController.java-----不满足null != papers && null != tester");
		}
		return json.toJSONString();
	}
}
