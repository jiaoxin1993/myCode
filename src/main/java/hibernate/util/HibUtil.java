package hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibUtil {
	private HibUtil(){
	}
	private static SessionFactory factory;
	//隔离各自线程数据，为各自线程做一份数据拷贝。每个线程使用它自身所对应的数据拷贝
	private static ThreadLocal<Session> sessionLocal = null;
	static{
		//加在hibernate配置
		/*
		 * 自定义配置文件
		 * File  file = new File("hibernate配置文件路径");
           Configuration config = new  Configuration.configure(file);
		 */
		Configuration conf = new Configuration();
		//默认配置文件名称为 hibernate.cfg.xml
		conf.configure();
		//hibernate默认事务不自动提交 需要手动提交
		factory = conf.buildSessionFactory();
		sessionLocal = new ThreadLocal<Session>();
	}
	public static Session getSession(){
		//判断是否已给该线程分配了数据拷贝
		// sessionLocal.get().isOpen() 可能获取对象存在但是已经被关闭了
		if(sessionLocal.get() != null && !sessionLocal.get().isOpen()){
			sessionLocal.set(null);
		}
		if(sessionLocal.get()==null){
			sessionLocal.set(factory.openSession());
		}
		return sessionLocal.get();
	}
}
