package in.async.hibernate.mapping;

import in.async.hibernate.util.HibernateUtil;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Student has many phone numbers
 */
class Student12M implements Serializable {
	private static final long serialVersionUID = 1L;
	private long sid;
	private String sname;
	private Set<Phone> studentPhoneNumbers;

	public Student12M() {
		super();
	}

	public long getSid() {
		return sid;
	}

	public Student12M(String sname, Set<Phone> studentPhoneNumbers) {
		super();
		this.sname = sname;
		this.studentPhoneNumbers = studentPhoneNumbers;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Set<Phone> getStudentPhoneNumbers() {
		return studentPhoneNumbers;
	}

	public void setStudentPhoneNumbers(Set<Phone> studentPhoneNumbers) {
		this.studentPhoneNumbers = studentPhoneNumbers;
	}

}

class Phone implements java.io.Serializable {

	private long phoneId;
	private String phoneType;
	private String phoneNumber;

	public Phone() {
	}

	public Phone(String phoneType, String phoneNumber) {
		this.phoneType = phoneType;
		this.phoneNumber = phoneNumber;
	}

	public long getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(long phoneId) {
		this.phoneId = phoneId;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}

public class One2ManyMappingDemo {
	private static final Logger log = Logger.getLogger(One2ManyMappingDemo.class);

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

			Set<Phone> phoneNumbers = new HashSet<Phone>();
			phoneNumbers.add(new Phone("house", "32354353"));
			phoneNumbers.add(new Phone("mobile", "9889343423"));

			Student12M student = new Student12M("Eswar", phoneNumbers);
			session.save(student);

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
