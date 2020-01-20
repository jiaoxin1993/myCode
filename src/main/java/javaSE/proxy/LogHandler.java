package javaSE.proxy;

public class LogHandler implements Moveable{
	Moveable m = null;
	public LogHandler(Moveable m) {
		this.m = m;
	}
	public void move() {
//		before();
		m.move();
//		after();

	}

//	@Override
//	public void before() {
//		System.out.println("开始记录日志");
//		
//	}
//
//	@Override
//	public void after() {
//		System.out.println("日志记录结束");
//		
//	}

}
