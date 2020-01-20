package javaSE.io;

import java.io.Serializable;

public class Model implements Serializable,Comparable<Model>{
	//  transuent修饰的成员变量在对象序列化时不被序列化
	String name;
	int age;
	Model(String name,int age){
		this.name = name;
		this.age = age;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name+"=="+age;
	}
	public int compareTo(Model arg0) {
		if(this.age==arg0.age){
			return 0;
		}else if(this.age>arg0.age){
			return 1;
		}else{
			return -1;
		}

	}
}
