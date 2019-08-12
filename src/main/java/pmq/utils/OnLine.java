package pmq.utils;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class OnLine implements MethodBeforeAdvice{
	/**
	* @param method 被调用方法对象
	* @param args 被调用的方法的参数
	* @param target 被调用的方法的目标对象
	* */

	//停止使用
	public void before(Method method, Object[] args1, Object target) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println(target.getClass().getName()+"的"+method.getName()+"方法被执行");
	}
}
