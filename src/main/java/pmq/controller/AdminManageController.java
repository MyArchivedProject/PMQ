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

import com.alibaba.fastjson.JSONObject;

import pmq.pojo.Admin;
import pmq.service.IAdminService;

@Controller
@RequestMapping(value = "admin/admin")
public class AdminManageController {
	@Resource(name = "adminService")
	private IAdminService adminService;
	@Resource(name = "admin")
	private Admin admin;

	// 添加管理员
	@ResponseBody
	@RequestMapping(value = "addAdmin")
	public String addAdmin(@Valid Admin admin, BindingResult result) {
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
		json.put("message", adminService.addAdmin(admin));
		return json.toJSONString();
	}

	/*
	 * // 修改管理员
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "updateAdmin") public String updateAdmin(@Valid
	 * Admin admin, BindingResult result) { JSONObject json = new
	 * JSONObject();// 保存返回客户端的信息 if (result.hasErrors()) { Map<String, Object>
	 * map = new HashMap<String, Object>(); List<FieldError> errors =
	 * result.getFieldErrors(); for (FieldError fieldError : errors) {
	 * map.put(fieldError.getField(), fieldError.getDefaultMessage()); }
	 * json.putAll(map); return json.toJSONString(); } // 判断是否添加成功 return
	 * json.toJSONString(); }
	 */

	// 删除管理员
	@ResponseBody
	@RequestMapping(value = "deleteAdmin")
	public String deleteAdmin(Integer id) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息

		// 判断是否添加成功
		if (null != id && adminService.deleteAdmin(id)) {
			json.put("message", "删除成功");
		} else {
			json.put("message", "删除失败");
		}
		return json.toJSONString();
	}

	// 通过等级搜索管理员
	@ResponseBody
	@RequestMapping(value = "getAdminByR")
	public String getAdminByR(String rank) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息
		if (rank == null) {
			System.out.println("非法连接AM");
			// json.put("error", "非法连接");
		} else {
			List<Admin> all = adminService.findAdmin(rank);
			json.put("all", all);
		}
		return json.toJSONString();
	}

	// 通过名字搜索管理员//模糊查询
	@ResponseBody
	@RequestMapping(value = "getAdminByN")
	public String getAdminByN(String name, String rank) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息
		admin.setName(name);
		admin.setRank(rank);
		List<Admin> all = adminService.findAdminByAdmin(admin);
		json.put("all", all);

		return json.toJSONString();
	}

	// 管理员修改自身的密码
	@ResponseBody
	@RequestMapping(value = "updateSelfPass")
	public String updateSelfPass(String adminId, String user, String oldPass, String newPass) {
		JSONObject json = new JSONObject();// 保存返回客户端的信息

		json.put("message", adminService.updateSelfPass(adminId, user, oldPass, newPass));
		return json.toJSONString();
	}
	// 重置管理员
		@ResponseBody
		@RequestMapping(value = "updateAdmin")
		public String updateAdmin(int adminId, String name, String pass) {
			JSONObject json = new JSONObject();// 保存返回客户端的信息
			if(adminId>0 && name!=null && pass!=null){
				admin.setId(adminId);
				admin.setName(name);
				admin.setPass(pass);
				json.put("message", adminService.updateAdmin(admin));
			}
			
			return json.toJSONString();
		}
}
