package thread;

/**
 * Created by admin on 2019/10/18.
 * synchronized
 * 同步方法 -- 继承
 * 子类同步方法覆盖父类同步方法，可以指定调用父类同步方法
 * 相对锁的可重入
 */
public class SynTest7 {
    public synchronized void m1(){
        System.out.println("Super m1方法执行-------start");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Super m1方法执行-------end");
    }
    public static void main(String[] args) {
        SynTest7 s = new SynTest7();
        s.new SynTest7_child().m1();

    }
    public class SynTest7_child extends SynTest7 {
        @Override
        public synchronized void m1() {
            System.out.println("m1方法执行-------start");
            super.m1();
            System.out.println("m1方法执行-------end");
        }
    }
}
