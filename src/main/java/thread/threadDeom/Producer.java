package thread.threadDeom;
/*
 *生产者
 */
public class Producer implements Runnable{
	//为了共享stack（栈）资源  ，则是stack对象作为构造函数参数来赋值
		Stack ss = null;
		public Producer(Stack ss){
			this.ss = ss;
		}
	public void run() {
		// TODO Auto-generated method stub
		char ch;
		for (int i = 0; i < 20; i++) {
			ch = (char) ('a'+i);//强制转换
			ss.push(ch);
			System.out.println("已经生成了"+(i+1)+"个=====");
		}
	}

}
