package jvm;

public class JvmTest {
	public static void main(String[] args) {
		//查看GC信息
		System.out.println("maxMemory:"+Runtime.getRuntime().maxMemory());
		System.out.println("freeMemory:"+Runtime.getRuntime().freeMemory());
		System.out.println("totalMemory:"+Runtime.getRuntime().totalMemory());

		byte[] b1 = new byte[1*1024*1024];
		System.out.println("分配了1M");

		System.out.println("maxMemory:"+Runtime.getRuntime().maxMemory());
		System.out.println("freeMemory:"+Runtime.getRuntime().freeMemory());
		System.out.println("totalMemory:"+Runtime.getRuntime().totalMemory());

		byte[] b2 = new byte[4*1024*1024];
		System.out.println("分配了4M");
		System.out.println("maxMemory:"+Runtime.getRuntime().maxMemory());
		System.out.println("freeMemory:"+Runtime.getRuntime().freeMemory());
		System.out.println("totalMemory:"+Runtime.getRuntime().totalMemory());
	}
}
