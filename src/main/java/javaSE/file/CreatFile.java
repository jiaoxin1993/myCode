package javaSE.file;

import java.io.File;
import java.io.IOException;

public class CreatFile {
	public static void main(String[] args) {
		create("wenx.txt");
	}
	public static void create(String fileName){
		String separator = File.separator;//获取系统分隔文件路径符号
		String directory = "mydir"+separator+"mmmmmm";//文件目录
		File f = new File("F:\\BaiduNetdiskDownload\\顺序.txt");
		if(f.exists()){
			//文件存在
			String path = f.getAbsolutePath();
			long size = f.length();
			System.out.println("文件路径是"+path+"====大小是"+size);
		}else{
			//创建文件父目录
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
				System.out.println("文件创建成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
