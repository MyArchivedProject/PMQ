package pmq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pmq.dao.AdminMapper;
import pmq.pojo.Admin;
import pmq.service.IAdminService;

@Service(value="adminService")
public class AdminServiceImpl implements IAdminService{
	@Resource(name="adminMapper")
	private AdminMapper adminDAO;
	@Resource(name="admin")
	private Admin admin;
	//添加管理员
	public String addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		String user=admin.getUser();
		String flag="添加失败";
		if(adminDAO.selectByUserId(user)==0){
			if(adminDAO.insert(admin)>0){
				flag="success";
			}
		}else{
			flag="此用户ID已经存在";
		}	
		return flag;
	}

	public String updateSelfPass(String adminId,String user,String oldPass,String newPass) {
		// TODO Auto-generated method stub
		String flag="修改失败,请刷新后重新";
		this.admin.setUser(user);
		this.admin.setPass(oldPass);
		if(login(this.admin)!=null){ //调用此类里的一个服务//验证旧密码是否正确
			Integer id=Integer.parseInt(adminId);
			this.admin.setId(id);
			this.admin.setPass(newPass);
			if(this.adminDAO.updateSelfPass(this.admin)>0){
				flag="success";
			}
		}else{
			flag="旧密码不正确";
		}		
		return flag;
	}

	public boolean deleteAdmin(int id) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(adminDAO.deleteByPrimaryKey(id)>0){
			flag=true;
		}
		return flag;
	}

	public List<Admin> findAdmin(String rank) {
		// TODO Auto-generated method stub
				
		return adminDAO.selectByRank(rank);
	}

	public Admin login(Admin admin) {
		// TODO Auto-generated method stub
		//登录成功则返回admin，不成功则返回null的admin
		return adminDAO.selectByUP(admin);
	}

	public List<Admin> findAdminByAdmin(Admin admin) {
		// TODO Auto-generated method stub
		if(admin.getName()==null){
			admin.setName("");
		}
		if(admin.getRank()==null){
			admin.setRank("");
		}else if(admin.getRank().equals("0")){
			admin.setRank("");
		}
		return adminDAO.selectAllBy(admin);
		//return adminDAO.selectByName(name);
	}

	public String updateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		if(adminDAO.updateByPrimaryKey(admin)>0){
			return "success";
		}else{
			return "重置失败";
		}
	}

}
