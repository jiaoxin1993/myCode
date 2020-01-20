package zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;
//使用原生zookeeper Api
public class ZookeeperBase {
	static final String CONNECT_ADDR ="192.168.230.128:2181,192.168.230.130:2181,192.168.230.131:2181";
	//session超时时间
	static final int SESSION_OUTTIME = 5000; //MS
	//阻塞程序执行，用于等待zookeeper连接成功，发送成功信号
	static final CountDownLatch cdl = new CountDownLatch(1);

	public static void main(String[] args) throws Exception{
		//观察者模式   Watcher事件处理器
		ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher(){
			@Override
			public void process(WatchedEvent event) {
				//获取事件状态
				KeeperState state = event.getState();
				//事件类型
				EventType type = event.getType();
				//如果是建立连接
				if(KeeperState.SyncConnected == state){
					//没有事件
					if(EventType.None == type){
						cdl.countDown();//减少锁的数量，释放所有等待的线程，如果计数为零。
						System.out.println("zookeeper 建立连接");
					}
				}
			}
		});
		//使当前线程等待直到锁向下计数为零，除非线程 interrupted，或指定的等待时间的流逝。
		cdl.await();
		//创建父节点  不支持递归创建子节点
		//节点类型 CreateMode.* 提供四种节点类型
		/**
		 * CreateMode.PERSISTENT  持久化节点
		 * CreateMode.PERSISTENT_SEQUENTIAL  持久化顺序节点
		 * CreateMode.EPHEMERAL  临时节点
		 * CreateMode.EPHEMERAL_SEQUENTIAL 临时顺序节点
		 */
		String node = zk.create("/testRoot", "testRoot".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		System.out.println(node);
		zk.close();
	}

}
