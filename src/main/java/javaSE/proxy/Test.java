package javaSE.proxy;


public class Test {
	public static void main(String[] args) throws Exception {
		//LogHandler h2 = new LogHandler(t);
		//TimeHandler h1 = new TimeHandler(h2);
		Tank k = new Tank();
		InvocationHandler inh = new TestHandler(k);
		// Moveable 具体需要代理类的所实现的接口    inh 在代理类方法前后需要具体操作的接口实现类
		Moveable m = (Moveable) Proxy.newProxyInstance(Moveable.class,inh);
		m.move();
//		Moveable m = h1;
//		m.move();
	}
}
