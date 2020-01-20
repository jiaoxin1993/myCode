package thread;

/**
 * Created by admin on 2019/10/18.
 * 同步方法 --锁与异常
 * 当同步方法中发生异常的时候，自动释放锁资源，不会影响其他线程的执行
 * 在业务中需要对异常进行处理
 */
public class SynTest8 {
    int count =0;
    synchronized void m(){
        System.out.println(Thread.currentThread().getName()+"start");
        while (true){
            count++;
            System.out.println(Thread.currentThread().getName()+count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count == 5){
                count = 1/0;
            }
        }
    }
    public static void main(String[] args) {
        final SynTest8 s = new SynTest8();
        new Thread(new Runnable() {
            public void run() {
                s.m();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                s.m();
            }
        }).start();
    }

}
