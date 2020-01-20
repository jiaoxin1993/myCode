package thread;

/**
 * Created by admin on 2019/10/21.
 * synchronized关键字
 * 锁变更问题
 * 同步代码一旦加锁后，那么会有一个临时的锁引用执行锁对象，和真实的引用无直接关联
 * 在锁未释放之前，修改锁对象引用，不会影响同步代码的执行
 */
public class SynTest10 {
    Object o = new Object();
    void m(){
        System.out.println(Thread.currentThread().getName()+"===start");
        synchronized (o){ //o 锁对象o 会在加锁后，有一个临时的锁引用执行对象 与this.o 不是一个 (可能指向内容一致)
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //o 其实指的是 this.o
                System.out.println(Thread.currentThread().getName()+o);
            }
        }
    }
    public static void main(String[] args) {
        final SynTest10 s = new SynTest10();
        new Thread(new Runnable() {
            public void run() {
                s.m();
            }
        },"thread1").start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s.o = new Object();
        new Thread(new Runnable() {
            public void run() {
                s.m();
            }
        },"thread2").start();

    }
}
