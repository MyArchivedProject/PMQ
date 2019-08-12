package pmq.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//此方法暂时不用,暂时换成拦截器
public class EncodingInterceptor extends HandlerInterceptorAdapter{
	@Override
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	            Object object) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		 return true;
	 }
}
