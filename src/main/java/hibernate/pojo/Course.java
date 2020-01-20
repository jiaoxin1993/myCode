package hibernate.pojo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
//课目
@Entity
public class Course {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String cname;
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="courses")
	private Set<Student> students = new HashSet<Student>();
	public void addStudent(Student student){
		students.add(student);
	}
	public int getId() {
		return id;
	}
	public String getCname() {
		return cname;
	}
	public Set<Student> getStudents() {
		return students;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	@Override
	public String toString() {
		return "Course [id=" + id + ", cname=" + cname + "]";
	}

}
