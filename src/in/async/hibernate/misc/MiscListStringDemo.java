package in.async.hibernate.misc;

import in.async.hibernate.util.HibernateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

class MiscListStringBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private MiscListPK id;
	private List<String> list;
	private Date ctime;

	public MiscListPK getId() {
		return id;
	}

	public void setId(MiscListPK id) {
		this.id = id;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
}

class MiscListPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String email;

	public MiscListPK() {
		super();
	}

	public MiscListPK(int id, String email) {
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
		MiscListPK other = (MiscListPK) obj;
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

public class MiscListStringDemo {
	private static final Logger log = Logger.getLogger(MiscListStringDemo.class);

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

			MiscListStringBean obj = new MiscListStringBean();
			MiscListPK pk = new MiscListPK(11, "java@java.com");
			List<String> list = new ArrayList<String>();
			list.add("jsp");
			list.add("servlet");

			obj.setId(pk);
			obj.setCtime(new Date());
			obj.setList(list);

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
