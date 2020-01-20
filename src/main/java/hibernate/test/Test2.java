package hibernate.test;

import hibernate.pojo.Course;
import hibernate.pojo.Student;
import hibernate.util.HibUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Test2 {
	public static void main(String[] args) {
		Session session = HibUtil.getSession();
		//hibernate默认事务不自动提交 需要手动提交
		Transaction tx = session.beginTransaction();
		Course c1 = new Course();
		Course c2 = new Course();
		Course c3 = new Course();
		c1.setCname("语文");
		c2.setCname("数学");
		c3.setCname("英语");
		Student s1 = new Student();
		Student s2 = new Student();
		Student s3 = new Student();
		s1.setSname("小白");
		s2.setSname("小红");
		s3.setSname("小黑");

		s1.addCourse(c3);
		s1.addCourse(c2);

		s2.addCourse(c3);
		s2.addCourse(c1);

		s3.addCourse(c3);
		s3.addCourse(c2);
		s3.addCourse(c1);
		/**
		 * 处于持久化状态的对象，当发生值改变时，hibernate能检测到，立刻修改数据的数据。如果在save之后，会执行两条sql语句，效率较低，建议对持久化对象值进行修改在save之前最好
		 */
		session.save(s1);
		session.save(s2);
		session.save(s3);
		tx.commit();
		session.close();
	}
}

