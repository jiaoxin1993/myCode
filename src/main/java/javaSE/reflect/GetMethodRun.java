package javaSE.reflect;

/*
 * 反射调用方法
 */
public class GetMethodRun {
	int i;
	public void a(){
		System.out.println("这是aaa");
	}
	void b(int i){
		this.i = i;
		System.out.println("bbbbbb"+this.i);
	}
	String c(){
		System.out.println("ccccccccc");
		return "天秀";
	}
}
