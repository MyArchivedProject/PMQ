package pmq.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import pmq.pojo.Admin;

public class OnlineHandlerInterceptor extends HandlerInterceptorAdapter {
	 @Override
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	            Object object) throws Exception {
		 String patten=".*/((login)|(validate))";
		 String path=request.getServletPath();
		// System.out.println(path);
		 if(path.matches(patten)){
			 return true;
		 }
		 Admin admin=(Admin) request.getSession().getAttribute("admin");
		 if(null==admin){
			response.sendRedirect("/PMQ/admin/login");
			 return false;
			 //如果不是超级管理员则不能进入 管理管理员的界面 与 相关操作
		 }else if("2".equals(admin.getRank())){
			 String pattenA=".*/(admin/admin)/.*";
			 if(path.matches(pattenA)){
				 return false;
			 }
		 }		 
		 return true;
	 }
}
