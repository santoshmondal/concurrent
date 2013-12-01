package in.async.hibernate.misc;

import in.async.hibernate.util.HibernateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

class MiscSetStringBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private MiscSetPK id;
	private Set<String> set;
	private Date ctime;

	public MiscSetPK getId() {
		return id;
	}

	public void setId(MiscSetPK id) {
		this.id = id;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
}

class MiscSetPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String email;

	public MiscSetPK() {
		super();
	}

	public MiscSetPK(int id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MiscSetPK other = (MiscSetPK) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		return true;
	}

}

public class MiscSetStringDemo {
	private static final Logger log = Logger.getLogger(MiscSetStringDemo.class);

	public static void main(String args[]) {
		insert();
	}

	public static void insert() {
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			MiscSetStringBean obj = new MiscSetStringBean();
			MiscSetPK pk = new MiscSetPK(12, "java@java.com");
			Set<String> set = new HashSet<String>();
			set.add("jsp");
			set.add("servlet");

			obj.setId(pk);
			obj.setCtime(new Date());
			obj.setSet(set);

			session.save(obj);

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
