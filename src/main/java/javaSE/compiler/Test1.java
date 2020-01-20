package javaSE.compiler;

import javaSE.proxy.Moveable;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Test1 {
	public static void main(String[] args) {
		Method[] methods = Moveable.class.getMethods();//ͨ�������ȡ��ķ�������
		for(Method m : methods){
			Type t = m.getReturnType();
			//System.out.println(m.getName());
			System.out.println(t);
		}
	}
}
