package thread;

/**
 * Created by admin on 2019/10/18.
 * 同步方法 同步方法和非同步方法的调用
 * 同步方法只影响其他线程调用同一个同步方法的锁问题，不影响其他线程调用非同步方法，或者调用其他资源的同步方法
 */
public class SynTest4 {
    public synchronized void m1(){
        System.out.println("m1方法执行-------start");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1方法执行-------end");
    }
    public void m2(){
        System.out.println("m2方法执行-------start");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2方法执行-------end");
    }
    public synchronized void m3(){
        System.out.println("m3方法执行-------start");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m3方法执行-------end");
    }
    public  class MyThread implements Runnable{
        int i;
        SynTest4 s;
        public MyThread(int i, SynTest4 s) {
            this.i = i;
            this.s = s;
        }
        public void run() {
            if(i==0){
                s.m1();
            }else if(i ==1){
                s.m2();
            }else if(i == 2){
                s.m3();
            }
        }
    }
    public static void main(String[] args) {
        /*
            创建静态内部类  new SynTest4.MyThread();
            创建内部类   SynTest4 s = new SynTest4();    s.new MyThread();
         */
        SynTest4 s = new SynTest4();
        new Thread(s.new MyThread(2,s)).start();
        new Thread(s.new MyThread(0,s)).start();
        new Thread(s.new MyThread(1,s)).start();


    }
}
