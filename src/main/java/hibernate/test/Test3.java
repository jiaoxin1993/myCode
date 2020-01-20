package hibernate.test;

import hibernate.pojo.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
public class Test3 {
	public static void main(String[] args) {
		//读取hibernate.cfg.xml文件
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		//2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
		SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		Session session = factory.openSession();
		Student student = session.get(Student.class, 1);
		System.out.println(student);
		System.out.println(student.getSname());
		session.close();
		Session session1 = factory.openSession();
		Student student2 = session1.get(Student.class, 1);
		System.out.println(student2.getSname());
	}
}
