package thread.threadDeom;


public class test {
	public static void main(String[] args) {
		Stack ss = new Stack();
		//创建生产和消费者对象
		Customer ct = new Customer(ss);
		Producer pd = new Producer(ss);
		//创建生成和消费线程并启动
		Thread t1 = new Thread(ct);
		Thread t2 = new Thread(pd);
		t1.start();
		t2.start();
		
	}
}
