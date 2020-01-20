package javaSE.file;

import java.io.File;

/*
 * 遍历某个文件目录
 * File 可以是文件，也可以是目录
 */
public class ShowFile {
	public static void main(String[] args) {
//		File[] roots = File.listRoots();  
//	    for (int i = 0; i < roots.length; i++) {  
//	      System.out.println(roots[i]);  
//	    }  获取系统盘符
		int len = 1;
		File f = new File("F:\\BaiduNetdiskDownload");
		System.out.println(f.getName());
		tree(f,len);
	}
	public static void tree(File f,int len){
		String src = "";//层次模板
		for (int i = 0; i < len; i++) {
			src +="    ";
		}
		//设置文件层次
		File[] childs = f.listFiles();//获取当前文件下的子File集合
		//遍历子file集合
		for (int i = 0; i < childs.length; i++) {
			System.out.println(src+childs[i].getName());//输出当前File名称
			//如果当前File不是文件  则递归调用 tree（）方法
			if(childs[i].isDirectory()){//isDirectory 是否是一个目录是返回true
				tree(childs[i],len+1);
			}
		}
	}

}
