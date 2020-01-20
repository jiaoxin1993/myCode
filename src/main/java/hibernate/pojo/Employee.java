package hibernate.pojo;
//员工
public class Employee {
	private int id;
	private String empName;
	// 关联一方
	// 编写一个对象, 不需要自己 new
	private Department dep;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpName() {
		return empName;
	}
	public Department getDep() {
		return dep;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public void setDep(Department dep) {
		this.dep = dep;
	}
    

}
