package pmq.utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.google.code.kaptcha.Producer;

public class Kaptcha {
	//@Resource(name="kaptchaProducer")
	//private Producer kap;
	public static Map<String,BufferedImage> generateImageCode(Producer kaptcha){
		//ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("kaptcha.xml");
		//Producer kaptcha=(Producer) ctx.getBean("kaptchaProducer");
		//ctx.close();
		String code=kaptcha.createText();//生成验证码
		
		BufferedImage codeImage=kaptcha.createImage(code);//生成验证码图片
		
		Map<String,BufferedImage>map=new HashMap<String,BufferedImage>();
		map.put(code, codeImage);
		//System.out.println(code);
		//ImageIO.write(codeImage, "png", new File("D:/a/aa/a.png"));
		return map;		
	}
}
