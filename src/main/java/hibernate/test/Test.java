package hibernate.test;

import hibernate.pojo.Department;
import hibernate.pojo.Employee;
import hibernate.pojo.User;
import hibernate.util.HibUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class Test {
	public static void main(String[] args) {
		Session session = HibUtil.getSession();
		//hibernate默认事务不自动提交 需要手动提交
		Transaction tx = session.beginTransaction();
		Employee e1 = new Employee();
		Employee e2 = new Employee();
		e1.setEmpName("张三1");
		e2.setEmpName("王五1");
		Department dep = new Department();
		dep.setDepName("采购部");
		//双向关联
		dep.getEmployees().add(e1);
		dep.getEmployees().add(e2);
		e1.setDep(dep);
		e2.setDep(dep);
		/**
		 * 处于持久化状态的对象，当发生值改变时，hibernate能检测到，立刻修改数据的数据。如果在save之后，会执行两条sql语句，效率较低，建议对持久化对象值进行修改在save之前最好
		 */
		session.save(dep);
		tx.commit();
		session.close();
	}
	//直接处于持久态
	public void toPersist(){
		Thread t = new Thread();
		Session session = HibUtil.getSession();
		Transaction tx = session.beginTransaction();
		//session.get(clazz,主键) 若对应主键=11的记录不存在，返回null
		//get方法现在session缓存（一级缓存）中查找，若无，再去SessionFactory（二级缓存 ，提前二级缓存存在）中查找，若无，再去数据库查找。若无 返回null
		User user = session.get(User.class, 11);

		//session.load(clazz,主键) 若对应主键=11的记录不存在，抛出异常
		//load一般用于我们可以保证这个记录一定存在的情况，该方法有懒加载模式（在记录真正使用时，再去查询数据库，通过代理实现）
		//User user = session.load(User.class, 11);
		user.setName("段赋");
		tx.commit();

	}
	//大文件
	public void toBigfile() throws Exception{
		Session session = HibUtil.getSession();
		Transaction tx = session.beginTransaction();
		InputStream is = new FileInputStream(new File("d:/a.gif"));

	}
}
