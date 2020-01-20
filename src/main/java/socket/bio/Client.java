package socket.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by admin on 2019/10/27.
 */
public class Client {
    public static void main(String[] args) {
        String host = null;
        int port = 0;
        if(args.length >2){
            host = args[0];
            port = Integer.parseInt(args[1]);
        }else{
            host = "127.0.0.1";
            port = 9999;
        }

        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            socket = new Socket(host,port);
            String message = null;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
            Scanner scanner = new Scanner(System.in);
            while(true){
                System.out.println("client reading ...");
                message = scanner.nextLine();
                if(message.equals("exit")){
                    break;
                }
                writer.println(message);
                writer.flush();//刷新输出流
                System.out.println(reader.readLine());
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
