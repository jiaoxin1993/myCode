package javaSE.proxy;
import java.lang.reflect.Method;
public class TimeHandler implements Moveable {
	long start;
	long end;
	InvocationHandler h = null;
	Method m = null;
	public TimeHandler(InvocationHandler h) {
		this.h = h;
	}
	public void move(){
		try{
			m=Moveable.class.getMethod("move");
			h.invoke(this,m);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}