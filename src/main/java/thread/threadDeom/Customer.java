package thread.threadDeom;
/*
 * 消费者
 */
public class Customer implements Runnable{
	//为了共享stack（栈）资源  ，则是stack对象作为构造函数参数来赋值
	Stack ss = null;
	public Customer(Stack ss){
		this.ss = ss;
	}
	public void run() {
		for (int i = 0; i < 20; i++) {
			ss.pop();
		}
	}
}
