package test;

import javax.annotation.Resource;

import org.junit.Test;

import com.google.code.kaptcha.Producer;

import pmq.dao.AdminMapper;
import pmq.pojo.Admin;
import pmq.utils.Kaptcha;

public class test_admin extends BaseTest{
	@Resource(name="admin")
	private Admin admin;	
	
	@Resource(name="admin")
	private Admin admina;	
	@Resource(name="adminMapper")
	private AdminMapper adminDAO;
	
	@Resource(name="kaptchaProducer")
	private Producer kaptcha;
	@Test
	public void test(){
		System.out.println(kaptcha);
		
		//System.out.println(adminDAO.selectByName("你"));
		/*admin.setRank("2");
		admin.setUser("3");
		admin.setPass("33");		
		System.out.println(adminDAO.insert(admin));*/
		/*System.out.println(adminDAO.selectByUP(admin));
		admina=adminDAO.selectByUP(admin);
		if(admina==null){
			System.out.println(admina);
		}*/		
	}
}
