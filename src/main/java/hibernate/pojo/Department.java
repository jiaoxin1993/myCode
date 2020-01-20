package hibernate.pojo;

import java.util.HashSet;
import java.util.Set;

//部门
public class Department {
	//设置主键自动 类型需为int
	private int id;
	private String depName;
	// 关联多方
	// Hibernate 框架默认的集合是 Set 集合,该集合必须要自己手动初始化
	private Set<Employee> employees = new HashSet<Employee>();
	public String getDepName() {
		return depName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public Set<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	
}
