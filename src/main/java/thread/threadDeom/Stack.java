package thread.threadDeom;
/*
 * 栈容器
 * 用于存放在生产物品供消费者使用
 *
 *
 * 在调用wait（）和notify（）方法时，一定要对竞争资源进行加锁，如果不加锁则会报异常
 * 		wait和notify都是配合while应用的，可以避免多线程并发判断逻辑失效问题
 * 		个人理解
 * 			jvm会给每一个对象都分配唯一的一把锁，这把锁是对象的
 * 			所以线程占用锁只是暂时，锁的释放、对象线程的等待和唤醒对象等待池里线程的应该都是由对象去操作的
 */
public class Stack {	
	char[]  zz = new char[6];
	int cnt = 0;//容器中有效元素个数
	public void push(char ch){
		
		synchronized (this) {
			while(zz.length==cnt){
				try {
					//如果压栈已经与容器数量一致，则将该线程置于等待
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.notify();//唤醒对象的waot pool（线程等待池里最早被置于等待的线程）
			//保证压栈和出栈操作互斥
			System.out.println("正在生产第"+cnt+"个,===内容"+ch+"前");
			zz[cnt]=ch;
			cnt++;
			System.out.println("正在生产第"+cnt+"个,===内容"+ch);
			
		}
		
	}
	public char pop(){
		char ch ;
		
		synchronized (this) {
			while(cnt==0){      //时时监听 换成  if（cnt==0）牵扯cup分配时间片段 如果判断和执行判断方法没有在一个时间片段内，判断条件可能改边。导致判断执行方法逻辑错误
				try {
					//如果容器没用有效元素，则将该线程置于等待
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.notify();//唤醒对象的waot pool（线程等待池里最早被置于等待的线程）
			//保证压栈和出栈操作互斥
			 ch =zz[cnt-1];
			System.out.println("正在消费第"+cnt+"个,===内容"+ch);
			 cnt--;
		}
		return ch;
	}
	
}
