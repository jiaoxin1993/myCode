package thread;

/**
 * Created by admin on 2019/10/18.
 * volatile关键字
 * volatile的可见性
 * 通知os操作系统底层，在cpu计算过程中，都要检查内存中数据的有效性。保证最新的内存数据被使用。
 */
public class VolTest1 {
    volatile boolean flag = true;
    void m(){
        System.out.println("m --start");
        while(flag){
        }
        System.out.println("m --end");
    }
    public static void main(String[] args) {
        final VolTest1 v = new VolTest1();
        new Thread(new Runnable() {
            public void run() {
                v.m();
            }
        }).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        v.flag = false;

    }
}
