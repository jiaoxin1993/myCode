package javaSE.compiler;

import javaSE.proxy.Moveable;
import javaSE.proxy.Tank;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

/*
 * 动态编译
 */
public class Test {
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String tr = "\r\n"; // 系统换行
		String str =
				"package me.jx.proxy;"+ tr+
						"		public class TimeHandler implements Moveable {"+ tr+
						"	    	long start;"+ tr+
						"			long end;" + tr+
						"			Moveable m = null;"+ tr+
						"			public TimeHandler(Moveable m) {"+ tr+
						"				this.m = m;"+ tr+
						"			}"+ tr+
						"			public void move(){" + tr+
						"				before();"+ tr+
						"				m.move();"+ tr+
						"				after();"+ tr+

						"			}"+ tr+
						"			@Override"+ tr+
						"			public void before() {"+ tr+
						"				start = System.currentTimeMillis();"+ tr+
						"				System.out.println(\"运行开始时间==\"+start);"+ tr+

						"				}"+ tr+
						"			@Override"+ tr+
						"			public void after() {"+ tr+
						"				end = System.currentTimeMillis();"+ tr+
						"				System.out.println(end);"+ tr+
						"				System.out.println(\"运行共用时间==\"+(end-start));"+ tr+

						"			}"+ tr+
						"		}";
		String fileName = System.getProperty("user.dir")+"/src/me/jx/proxy/TimeHandler.java"; // 定义文件路径及名称
		System.out.println(fileName);
		File f = new File(fileName); // 创建文件
		FileWriter fw = new FileWriter(f); // 创建文件写入对象
		fw.write(str); // 写入操作
		fw.flush();
		fw.close();
		//java 文件已创建成功
		
		/*
		 * 要获取编译对象 不能使用jre纯运行环境 需要改成 jdk编译环境
		 */
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		System.out.println(compiler);
		StandardJavaFileManager sf = compiler.getStandardFileManager(null, null, null);//获取文件管理器  参数为null 即为默认值 
		Iterable units = sf.getJavaFileObjects(fileName);//根据filename参数 获取需要编译对象的结果集（几个可以传多个文件路径）
		CompilationTask t= compiler.getTask(null, sf, null, null, null, units);//获取指令执行对象
		t.call();//执行指令
		sf.close();
		//Java对象已编译成class文件
		
		/*
		 * 将class加载到内存中,由于class文件没有在bin目录下 ，不能使用classloader直接去加载到内存中去
		 */
		URL[] urls = new URL[] {new URL("file:/"+System.getProperty("user.dir")+"/src") };
		System.out.println("file:/"+System.getProperty("user.dir")+"/src");
		URLClassLoader ul = new URLClassLoader(urls);
		Class c = ul.loadClass("me.jx.proxy.TimeHandler");
		System.out.println(c);
		//已加载之内存中

		//创建该类的对象
		Constructor ctt = c.getConstructor(Moveable.class); // 获取参数为Moveable的构造方法
		Moveable m = (Moveable) ctt.newInstance(new Tank());
		m.move();

	}
}
