package in.async.hibernate.mapping;

import in.async.hibernate.util.HibernateUtil;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/*
 * Many Student has one address
 */
class StudentM implements Serializable {
	private static final long serialVersionUID = 1L;
	private long sid;
	private String sname;
	private Address1 saddress;

	public StudentM() {
		super();
	}

	public StudentM(String sname, Address1 saddress) {
		super();
		this.sname = sname;
		this.saddress = saddress;
	}

	public long getSid() {
		return sid;
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

	public Address1 getSaddress() {
		return saddress;
	}

	public void setSaddress(Address1 saddress) {
		this.saddress = saddress;
	}

}

class Address1 implements Serializable {

	private static final long serialVersionUID = 1L;
	private long aid;
	private String street;
	private String city;
	private String state;
	private String zipcode;

	public Address1() {
		super();
	}

	public Address1(String street, String city, String state, String zipcode) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
}

public class Many2OneMappingDemo {
	private static final Logger log = Logger.getLogger(Many2OneMappingDemo.class);

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

			Address1 address = new Address1("OMR Road", "Chennai", "TN", "600097");
			StudentM student1 = new StudentM("Eswar", address);
			StudentM student2 = new StudentM("Joe", address);
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
