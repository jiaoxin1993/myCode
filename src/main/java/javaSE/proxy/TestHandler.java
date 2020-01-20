package javaSE.proxy;

import java.lang.reflect.Method;

public class TestHandler implements InvocationHandler {
	private Object obj;
	public TestHandler(Object obj) {
		this.obj = obj;
		// TODO Auto-generated constructor stub
	}
	public void invoke(Object o, Method m) throws Exception{
		System.out.println("方法前处理");
		m.invoke(obj,new Object[]{});
		System.out.println("谁是代理对象"+o.getClass().getName());
		System.out.println("方法处理后");
	}

}
