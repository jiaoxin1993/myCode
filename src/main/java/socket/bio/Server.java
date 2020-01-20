package socket.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2019/10/27.
 */
public class Server {
    public static void main(String[] args) {
        int prot = getPort(args);
        ServerSocket server = null;
        //使用线程池
        ExecutorService service = Executors.newFixedThreadPool(50);
        try {
            server = new ServerSocket(prot);
            System.out.println("server started!");
            while(true){
               Socket socket = server.accept();
                service.execute(new Handler(socket));
            }
        } catch (Exception e) {
        	// TODO: handle exception
            e.printStackTrace();
        }
    	System.out.println();

    }
    static class Handler implements Runnable{
        Socket socket = null;
        public Handler(Socket socket){
            this.socket = socket;
        }
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
                String readMessage = null;
                while(true){
                    System.out.println("server reading ...");
                    if((readMessage = reader.readLine()) == null){
                      break;
                    }
                    System.out.println(readMessage);
                    writer.println("server recive : "+readMessage);
                    writer.flush();//刷新输出流
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }finally {
                if (socket != null){
                    try {
                        socket.close();
                    } catch (Exception e) {
                    	e.printStackTrace();
                    }
                }
                socket = null;//socket.close() 不一定立刻就将socket对象清理掉 此处引用变为null
                if(writer != null){
                    writer.close();
                }
                if(reader != null){
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
    private static int getPort(String[] args){
        if(args.length > 0){
            try {
                return Integer.parseInt(args[0]);
            } catch (Exception e) {
            	// TODO: handle exception
                return 9999;
            }
        }else{
            return 9999;
        }
    }
}
