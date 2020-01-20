package thread;

/**
 * Created by admin on 2019/10/21.
 * synchronized关键字
 * 常量问题
 * 在定义同步代码块时，不要使用常量对象作为锁对象
 */
public class SynTest11 {
    String s1 = "hello";
    String s2 = "hello";
    String s3 = new String("hello"); //new 关键字，一定是在堆中创建一个新的对象
    Integer i1 = 1;
    Integer i2 = 1;
    void m1(){
        synchronized(i1){
            System.out.println("m1()");
            while(true){

            }
        }
    }
    void m2(){
        synchronized(i2){
            System.out.println("m2()");
            while(true){

            }
        }
    }
    public static void main(String[] args) {
        final SynTest11 s =new SynTest11();
        new Thread(new Runnable() {
            public void run() {
                s.m1();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                s.m2();
            }
        }).start();

    }
}
