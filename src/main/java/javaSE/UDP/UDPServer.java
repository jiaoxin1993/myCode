package javaSE.UDP;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
	public static void main(String[] args) throws Exception {
		byte[] buf = new byte[1024];//创建一个字节数组，用来存贮客户端包的数据
		DatagramPacket dp = new DatagramPacket(buf,buf.length);//构造用于接收长度最大为buf.length的数据包
		DatagramSocket ds = new DatagramSocket(5210);//监听一个端口用来接收客户端传递的包   注：tcp端口和udp端口不是一种，所以两者端口号可以重复
		while(true){
			ds.receive(dp);//将接受数据放到dp数据包中，实际是放到了buf字节数组  receive为阻塞性方法
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			DataInputStream dis = new DataInputStream(bais);
			System.out.println(dis.readLong());
		}

	}
}
