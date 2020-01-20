package netty.Serializable;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by admin on 2019/10/31.
 * 压缩工具
 */
public class GzipUtils {
    public static void main(String[] args) {
        String msg = "dsfasdfkljljlkjjkllkjljljlksgfgggsdsdssewrwhfdgaadfsdfwegdhnghjghjrtd";


        try {
            System.out.println("压缩前"+msg.getBytes().length);
            byte[] tar = zip(msg.getBytes());
            System.out.println("压缩后"+tar.length);
            System.out.println(new String(unzip(tar)));
        } catch (Exception e) {
        	e.printStackTrace();
        }


    }
    /*
        解压缩
        source 源数据 需要解压的数据
        return 解压后的数据。恢复的数据
     */
    public static byte[] unzip(byte[] source){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(source);
        ZipInputStream zipIn = null;
        try {
            //JDK提供的，专门用于压缩和解压缩使用的流对象，可以处理字节数组
            zipIn = new ZipInputStream(in);
            byte[] temp = new byte[512];
            int length = 0;

            while((length=zipIn.read(temp,0,temp.length))!=-1){
                out.write(temp,0,length);
                length = 0;
            }
        } catch (Exception e) {
        	e.printStackTrace();
            //return null;
        }finally {
            try {
                zipIn.close();
                out.close();
            } catch (Exception e) {
            	// TODO: handle exception
            }

        }

        return  out.toByteArray();
    }
    /*
        压缩
            source 源数据，需要压缩的数据
            return 压缩后的数据
     */
    public static byte[] zip(byte[] source) throws Exception{
        ByteArrayOutputStream out =  new ByteArrayOutputStream();
        ZipOutputStream zipout = new ZipOutputStream(out);
        zipout.write(source);
        zipout.flush();
        byte[] target = out.toByteArray();
        zipout.close();
        return target;
    }
}
