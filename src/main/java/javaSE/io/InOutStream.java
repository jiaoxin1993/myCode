package javaSE.io;

import java.io.*;

public class InOutStream {
	public static void main(String[] args) throws IOException {
		copyFile("E:/数据库.txt","E:/数据库1.txt");
	}
	public static File copyFile(String source, String dest)
			throws IOException {
		InputStream input = null;
		OutputStream output = null;
		dest = dest.replaceAll("\\\\", "/");
		String destlm = dest.substring(0,dest.lastIndexOf("/")) ;
		File copy = new File(destlm);
		if(!(copy.exists())){
			copy.mkdirs();
		}
		copy = new File(dest);
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(copy);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
		return copy;
	}
}
