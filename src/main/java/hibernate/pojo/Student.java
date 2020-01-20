package hibernate.pojo;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//学生
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String sname;
	@ManyToMany(cascade=CascadeType.ALL)
	private Set<Course> courses = new HashSet<Course>();
	public void addCourse(Course course){
		courses.add(course);
	}
	public int getId() {
		return id;
	}
	public String getSname() {
		return sname;
	}
	public Set<Course> getCourses() {
		return courses;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", sname=" + sname + ", courses="
				+ courses + "]";
	}
	
}
