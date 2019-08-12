package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.code.kaptcha.Producer;

import pmq.dao.EvaluationMapper;
import pmq.pojo.Evaluation;
import pmq.service.IEvaluationService;
//
public class test_eva extends BaseTest{
	@Resource(name="evaluationService")
	private IEvaluationService evaService;
	
	@Resource(name="evaluationMapper")
	private EvaluationMapper evaDAO;
	@Resource(name="evaluation")
	private Evaluation eva;
	
	
	@Test
	public void test0(){
		System.out.println(evaDAO.selectEvaByPost("aaa"));
		
	}
	//@Test
	public void test1(){
		eva.setId(80);
		eva.setEvaA("AA");
		eva.setMiniA(1);
		eva.setMiniB(1);
		eva.setMiniC(1);
		eva.setMiniD(1);
		eva.setMiniE(1);
		//eva.setMiniF(1);
		eva.setEvaB("B");
		eva.setEvaC("g");
		eva.setEvaD("g");
		eva.setEvaE("g");
		eva.setEvaF("g");
		eva.setMultiIndex("43");
		//System.out.println(evaService.updateEva(eva));
		System.out.println(eva.getFlag());
		System.out.println(eva.getMiniF());
		System.out.println(evaDAO.updateByPrimaryKey(eva));
		//System.out.println(evaDAO.selectByPrimaryKey(79).getPost());
	}
	//@Test
	public void test34() throws IOException{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("kaptcha.xml");
		//声明dao接口，对数据库进行具体操作
		//声明SqlSession类，用来获取dao接口的操作对象
		Producer kaptcha=(Producer) ctx.getBean("kaptchaProducer");
		String code=kaptcha.createText();
		//生成验证码图片
		BufferedImage codeImage=kaptcha.createImage(code);
		ImageIO.write(codeImage, "png", new File("D:\\a\\aa\\a.png"));
		ctx.close();
	}
	
	@Test
	public void importEva() throws FileNotFoundException, IOException{
		//System.out.println(evaService);
		//System.out.println(evaService.importEva("src/main/resources/xls/evaluation.xls"));
		//System.out.println(evaService.findAll(""));
		/*String a="dsa dsd   dd";
		String aa[]=a.split(" ");
		for(int i=0;i<aa.length;i++){
			System.out.println(aa[i]+"--");
		}*/
		
	}
	
	//@Test
	public void test(){
		String cellString="";
		//int cellString=1;
		Pattern pat=Pattern.compile("[0-9]*");
		if(!pat.matcher(cellString).matches()){
			cellString="0";
		}else{
			cellString="0";
		}
		
		System.out.println(Integer.valueOf(cellString));
	}
}
