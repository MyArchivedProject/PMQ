package pmq.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import pmq.pojo.Admin;
import pmq.service.IAdminService;

@Controller
@RequestMapping(value = "admin")
public class AdminController {
	@Resource(name = "adminService")
	private IAdminService adminService;
	@Resource(name = "admin")
	private Admin admin;

	// 跳转入管理员登录界面
	@RequestMapping(value = "login")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("admin/login");
	}

	// 进行登录验证
	@ResponseBody
	@RequestMapping(value = "validate")
	public String validate(HttpServletRequest request, HttpServletResponse response) {
		// @RequestParam("data") String data,
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();// 保存返回客户端的信息

		// 获取客户端传过来的json字符串
		String data = request.getParameter("data");
		// 防止非法连接//应该进行正则表达式验证
		if (null == data || !((data.startsWith("{") && data.endsWith("}")))) {
			json.put("message", "非法连接1");
			return json.toJSONString();
		}
		// System.out.println(data);
		JSONObject obj = JSONObject.parseObject(request.getParameter("data"));// 获取来自客户端的信息
		// 防止非法连接
		if (obj == null) {
			json.put("message", "非法连接2");
			return json.toJSONString();
		}
		// 判断是否已经有用户在这个session上登录啦，防止两个用户同时共享一个session
		Admin admin = (Admin) session.getAttribute("admin");
		if (null != admin && null != admin.getUser()) {
			if (!admin.getUser().equals(obj.getString("user"))) {
				json.put("message", "一个游览器只能登录一个账户");
				return json.toJSONString();
			}
		}
		String code = obj.getString("code");
		// 判断验证码是否正确
		if (code.equalsIgnoreCase((String) session.getAttribute("code"))) {
			// System.out.println(obj.getString("user"));
			// System.out.println(admin);
			String user = obj.getString("user");
			this.admin.setUser(user);
			this.admin.setPass(obj.getString("pass"));
			// 判断是否登录成功
			if (adminService.login(this.admin) == null) {
				json.put("message", "账户或者密码错误");
			} else {
				session.removeAttribute("code");
				session.setAttribute("admin", adminService.login(this.admin));
				json.put("message", "true");
			}
		} else {
			json.put("message", "验证码错误");
		}
		return json.toJSONString();
	}

	// 验证成功//跳入后台界面
	@RequestMapping(value = "success")
	public ModelAndView loginSuccess(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/head");
		return mav;
	}

	// 获取用户的用户名和等级，当登录成功后返回给后台
	@ResponseBody
	@RequestMapping(value = "getUserInfo")
	public String getUserName(HttpServletRequest request, HttpServletResponse response) {
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		admin.setPass("***");
		return JSON.toJSONString(admin);
	}

	// 进行安全退出
	@RequestMapping(value = "logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("admin");
	}

	// 测试管理界面
	@RequestMapping(value = "paper")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/paper");
		return mav;
	}

	// 题库管理界面
	@RequestMapping(value = "ques")
	public ModelAndView ques(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/ques");
		return mav;
	}

	// 等级评价管理界面
	@RequestMapping(value = "eva")
	public ModelAndView eva(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/eva");
		return mav;
	}

	// 职位指标管理界面
	@RequestMapping(value = "target")
	public ModelAndView target(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/target");
		return mav;
	}

	// 管理员管理界面
	@RequestMapping(value = "admin")
	public ModelAndView admin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin");
		return mav;
	}

	// 职位管理界面
	@RequestMapping(value = "post")
	public ModelAndView post(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("admin/post");
	}

	// 更新题目界面
	@RequestMapping(value = "quesUpdate")
	public ModelAndView quesUpdate(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("admin/quesUpdate");
	}

	// 后台头部界面
	@RequestMapping(value = "head")
	public ModelAndView head() {
		return new ModelAndView("admin/head");
	}

	// 主观题分数批改界面
	@RequestMapping(value = "mark")
	public ModelAndView mark(Integer testerId, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("admin/mark");
		request.getSession().setAttribute("testerId", testerId);
		return view;
	}
	// 试卷详情界面
	@RequestMapping(value = "report")
	public ModelAndView report(Integer testerId, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("admin/report");
		return view;
	}
}
