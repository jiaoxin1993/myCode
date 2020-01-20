package javaSE.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 类的序列化与反序列化
 */

public class IoClass{
	public static void main(String[] args) {
		ObjectOutputStream oos = null;
		ObjectInputStream  ois = null;
		Model m = new Model("离人刺",111);
		Model m2 =null;
		try {
			FileOutputStream fos  = new FileOutputStream("F:\\classTest.txt");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(m);//将创建的类序列化到classTest 中
			//取出序列化的类
			ois = new ObjectInputStream(new FileInputStream("F:\\classTest.txt"));
			m2 = (Model) ois.readObject();
			System.out.println(m2.name);
			System.out.println(m2.age);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
