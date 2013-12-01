package in.async.hibernate.mapping;

import in.async.hibernate.util.HibernateUtil;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/*
 * Many Student many Course
 */
class StudentM2M implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long studentId;
	private String studentName;
	private Set<Course> courses;

	public StudentM2M() {
	}

	public StudentM2M(String studentName) {
		this.studentName = studentName;
	}

	public StudentM2M(String studentName, Set<Course> courses) {
		this.studentName = studentName;
		this.courses = courses;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
}

class Course implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long courseId;
	private String courseName;

	public Course() {
	}

	public Course(String courseName) {
		this.courseName = courseName;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

}

public class Many2ManyMappingDemo {

	private static final Logger log = Logger.getLogger(Many2ManyMappingDemo.class);

	public static void main(String[] args) {
		insert();
	}

	public static void insert() {
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			Set<Course> courses = new HashSet<Course>();
			courses.add(new Course("Maths"));
			courses.add(new Course("Computer Science"));

			StudentM2M student1 = new StudentM2M("Eswar", courses);
			StudentM2M student2 = new StudentM2M("Joe", courses);
			session.save(student1);
			session.save(student2);

			txn.commit();
		} catch (HibernateException he) {
			if (txn != null) {
				txn.rollback();
			}
			log.error("Hibernate Exeption", he);
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			log.error("Generic Exeption", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
