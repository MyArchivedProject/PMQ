package test;

import java.util.Arrays;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.junit.Test;

import pmq.pojo.Admin;
import pmq.service.IAdminService;

public class test_Service extends BaseTest{

	@Resource(name="adminService")
	private IAdminService adminService;
	@Test
	public void test(){
		/*Pattern pat = Pattern.compile("[0-9]*");
		if (!pat.matcher(" ").matches()) {
			System.out.println("aa");
		}*/
		int a[] = {10, 5, 8};
		
		int min = Arrays.stream(a).max().getAsInt();
		System.out.println(min);
	}
}
