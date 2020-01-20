package javaSE.UDP;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPClient {
	public static void main(String[] args) throws IOException {
		long n = 100000L;//想要发送的数据
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeLong(n);//写到了 baos 自建字节数组中去了
		byte[] buf = baos.toByteArray();//有多少内容就建多大数组
		DatagramPacket dp = new DatagramPacket(buf,buf.length,new InetSocketAddress("127.0.0.1",5210));//数据包中要包含发送地址和端口，tcp则直接建立长连接不需要如此
		DatagramSocket ds = new DatagramSocket(5999);//定义发送数据包端口
		ds.send(dp);//将dp中的书中包发出
		ds.close();//关闭端口
	}
}
