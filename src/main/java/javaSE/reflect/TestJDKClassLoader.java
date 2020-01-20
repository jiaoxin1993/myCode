package javaSE.reflect;

import java.lang.reflect.Method;

/*
 * 通过遍历获取类加载器的引用关系
 * * 当反射对象的accessible标志设为true时，则表示反射的对象在使用时应该取消Java语言访问检查。反之则检查。由于
 * JDK的安全检查耗时较多，所以通过setAccessible(true)的方式关闭安全检查来提升反射速度
 */
public class TestJDKClassLoader {
	//	public static void main(String[] args) {
////		ClassLoader c = TestJDKClassLoader.class.getClassLoader();
////		while(c!=null){
////			System.out.println(c.getClass().getName());
////			c = c.getParent();//获取当讲类加载器，引用类加载器对象
////		}
//	}
	public static void main(String[] args) throws Exception {
		Class g = Class.forName("junit.reflect.GetMethodRun");
//		GetMethodRun gm =  (GetMethodRun) g.newInstance();//通过反射拿到对象
		//getDeclaredMethod可以获取到所有方法，而getMethod只能获取public 
		//取消对访问控制修饰符的检查
		//field.setAccessible(true);
		Method[] methods = g.getMethods();
//		Method[] methods = gm.getClass().getMethods();

		for (Method method : methods) {
			//先拿到方法参数
			System.out.println(method.getName());
			for (Class param : method.getParameterTypes()) {

				System.out.println(param.getName());
			}
			String s = method.getReturnType().getName();
			System.out.println("    "+s);
		}

	}
}
