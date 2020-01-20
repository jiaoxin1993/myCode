package socket.chatRoom;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;



public class ChatClient extends Frame{
    TextField text = new TextField();
    TextArea textContent = new TextArea();//长文本框
    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    boolean connected =false;
    public static void main(String[] args) {
        new ChatClient().launchaFrame();
    }
    public void launchaFrame(){
        this.setBounds(400, 300, 300, 300);
        //使用默认的布局管理器
        add(text,BorderLayout.SOUTH);
        add(textContent,BorderLayout.NORTH);
        pack();//自适应大小
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                disConnect();//关闭时清空资源
                System.exit(0);
            }
        });
        text.addActionListener(new TextListener());
        this.setVisible(true);
        connect();
        new Parse().start();//启动接收消息监听线程

    }
    private class TextListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = text.getText().trim();
            text.setText("");
            send(s);
        }
    }
    private void connect(){
        try {
            s = new Socket("127.0.0.1",8888);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            System.out.println("连接成功");
            connected = true;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void disConnect(){
        try {
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void send(String str){
        try {
            dos.writeUTF(str);
            dos.flush();//刷新
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    class Parse extends Thread{
        @Override
        public void run() {
            try {
                while(connected){
                    String str = dis.readUTF();
                    System.out.println(str);
                    textContent.setText(textContent.getText()+"\n"+str);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }
}

