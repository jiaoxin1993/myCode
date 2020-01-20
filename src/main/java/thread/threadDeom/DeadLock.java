package thread.threadDeom;
/*
 * 死锁
 */
public class DeadLock implements Runnable {
	//因为为静态变量 两个线程访问的为 锁定的是相同的 s1 和s2  两个线程拿着相互需要的锁资源不放导致两者都无法进行
	int flag =1;
	static String s1 = new String("wen");
	static String s2 = new String("wen");
	public void run() {
		System.out.println("flag是==="+flag);
		if(flag==1){
			synchronized(s1){
				System.out.println("我占用s1");
				synchronized (s2) {
					System.out.println("我还要占用s2");
				}
			}
		}
		if(flag==0){
			synchronized(s2){
				System.out.println("我占用s2");
				synchronized (s1) {
					System.out.println("我还要占用s1");
				}
			}
		}
		
	}
	public static void main(String[] args) {
		DeadLock dl1 = new DeadLock();
		DeadLock dl2 = new DeadLock();
		dl1.flag = 0;
		dl2.flag = 1;
		Thread t1 = new Thread(dl1);
		Thread t2 = new Thread(dl2);
		t1.start();
		t2.start();
	}
	
}
