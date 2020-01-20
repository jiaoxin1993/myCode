package javaSE.io;

import java.io.PrintStream;
import java.util.Scanner;

/*
 * 打印日志
 * 键盘录入，正常输出打印到文件中，异常打印到err中
 */
public class LogOutput {
	public static void main(String[] args) {
		PrintStream  psOut = null;
		PrintStream  errOut = null;
		Scanner sc = null;
		try {
			psOut = new PrintStream("F:\\logtest.txt");//建立与logtest关联的管道
			errOut = new PrintStream("F:\\errtest.txt");//建立与errtest关联的管道
			sc = new Scanner(System.in);//获取键盘输入对象
			int num;
			System.setOut(psOut);//将输出与psOut绑定，默认与显示器绑定
			System.setErr(errOut);//与上述一致
			while(true){
				num = sc.nextInt();
				System.out.println(num);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("错误信息是：");//乱码  与文件编码格式有关
			e.printStackTrace();
		}


	}
}
