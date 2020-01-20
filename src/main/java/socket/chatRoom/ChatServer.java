package socket.chatRoom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ChatServer {
    List<Client> clients = new ArrayList<Client>();
    public static void main(String[] args) {
        new ChatServer().start();
    }
    public void start(){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8888);
            System.out.println("服务器启动");
            while(true){
                Socket s = ss.accept();//线程阻塞
                System.out.println("客户端连接");
                Client client = new Client(s);
                clients.add(client);
                client.start();
            }
        } catch (Exception e) {
            System.out.println("异常关闭");
        }finally{
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class Client extends Thread{
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        boolean started = false;
        Client(Socket s){
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                started = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        public void send(String str) {
            try {
                dos.writeUTF(str);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                clients.remove(this);
            }
        }
        @Override
        public void run() {
            try {
                while(started){
                    String str;
                    str = dis.readUTF();
                    System.out.println(str);
                    System.out.println(clients.size());
                    for (int i = 0; i < clients.size(); i++) {
                        Client client = clients.get(i);
                        client.send(str);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    if(dis != null)
                        dis.close();
                    if(dos != null)
                        dos.close();
                    if(s != null)
                        s.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }
}

