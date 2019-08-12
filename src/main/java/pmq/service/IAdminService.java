package pmq.service;

import java.util.List;

import pmq.pojo.Admin;

public interface IAdminService {
	public String addAdmin(Admin admin);//添加管理员
	
	public String updateSelfPass(String adminId,String user,String oldPass,String newPass);//更新管理员自身的密码 
	
	public boolean deleteAdmin(int id);//删除管理员
	
	public List<Admin> findAdmin(String rank);//通过管理员级别查找管理员，可以进行模糊查询,即既可以查找全部也可以通过rank查找
	
	//登录操作//通过管理员Id和密码获取管理员
	public Admin login(Admin admin);
	
	//通过管理员进行模糊查询
	public List<Admin> findAdminByAdmin(Admin admin);
	
	public String updateAdmin(Admin admin);
}
