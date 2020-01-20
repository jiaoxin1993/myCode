package ftp;


import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2019/11/18.
 */
public class FtpTest {
    public static void main(String[] args) throws IOException {
        FTPClient ftp = new FTPClient();
        //先连接后登录
        ftp.connect("192.168.230.128", 21);
        ftp.login("ftpuser", "ftpuser");
        //设置文件类型 不设置会导致文件下载不正常
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        InputStream is = new FileInputStream(new File("F:/公共ip.png"));
        ftp.storeFile("abc.png",is);
        ftp.logout();
        System.out.println("上传成功");
    }
}
