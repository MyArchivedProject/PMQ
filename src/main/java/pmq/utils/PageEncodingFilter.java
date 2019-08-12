package pmq.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageEncodingFilter implements Filter{
	private String encoding="";
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest)arg0;
        HttpServletResponse response=(HttpServletResponse)arg1;
        
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        //过滤通行证
        chain.doFilter(request, response);
	}
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.encoding = arg0.getInitParameter("Encoding");
	}

}
