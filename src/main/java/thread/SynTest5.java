package thread;

/**
 * Created by admin on 2019/10/18.
 * synchronized关键字
 * 同步方法  多方法调用原子性问题（业务）
 * 同步方法只能保证当前方法的原子性，不能保证多个业务方法之间的相互访问的原子性
 * 注意在商业开发中，多方法要求结果访问原子操作，需要多个方法都加锁，且锁定统一个资源
 */
public class SynTest5 {
    private double d = 0.0;
    public synchronized void m1(double d){
        try {
            //相当于复杂业务所需时间
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.d = d;
    }
    public double m2(){
        return this.d;
    }
    public static void main(String[] args) {
        final SynTest5 s = new SynTest5();
        new Thread(new Runnable() {
            public void run() {
                s.m1(100);
            }
        }).start();
        System.out.println(s.m2());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(s.m2());
    }
}
