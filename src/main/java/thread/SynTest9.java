package thread;

/**
 * Created by admin on 2019/10/21.
 * synchronized关键字
 * 不推荐使用synchronized关键字修改方法 锁粒度太大
 *
 */
public class SynTest9 {
    synchronized void m1(){
        //前置逻辑
        System.out.println("同步逻辑");
        //后置逻辑
    }
    void m2(){
        //前置逻辑
        synchronized (this){
            System.out.println("同步逻辑");
        }
        //后置逻辑
    }
}
