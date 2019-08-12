package pmq.controller;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Producer;

import pmq.utils.Kaptcha;

@Controller
@RequestMapping(value = "common")
public class CommonController {
	@Resource(name="kaptchaProducer")
	private Producer kaptcha;//给获取验证码的工具类，传进kaptcha
	
	@RequestMapping(value = "getCode")
	public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// 设置禁止缓存
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String code = null;
		// 取出一个元素,输出验证码图片至页面，并保存验证码字符
		for (Map.Entry<String, BufferedImage> entry : Kaptcha.generateImageCode(kaptcha).entrySet()) {
			if (code == null) {
				// 输出
				ServletOutputStream out = response.getOutputStream();
				ImageIO.write(entry.getValue(), "jpg", out);
				code = entry.getKey();
				session.setAttribute("code", code);
				try {
					out.flush();
				} finally {
					out.close();
				}
			}
		}
	}
}
