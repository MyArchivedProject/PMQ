package pmq.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//当复制应用变量类型时（克隆），需要类
public class MyCloneUtil {
	private MyCloneUtil() {
		throw new AssertionError();
	}
	// Discription:[深度复制方法,需要对象及对象所有的对象属性都实现序列化]

	@SuppressWarnings("unchecked")
	public static <T> T clone(T obj) throws Exception {
		// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		// 将流序列化成对象
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		return (T) ois.readObject();
	}
}
