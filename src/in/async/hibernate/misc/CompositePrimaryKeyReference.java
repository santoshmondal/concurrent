package in.async.hibernate.misc;

import in.async.hibernate.util.HibernateUtil;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class CompositePrimaryKeyReference implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(CompositePrimaryKeyReference.class);

	private ReferencePrimaryKey id;
	private String email;

	private Date utime;
	private Date ctime;

	public CompositePrimaryKeyReference() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public ReferencePrimaryKey getId() {
		return id;
	}

	public void setId(ReferencePrimaryKey id) {
		this.id = id;
	}

	// Main and Business methods
	public static void main(String args[]) {
		log.info("Main Executions::");
		update();
	}

	public static void insert() {
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			CompositePrimaryKeyReference obj = new CompositePrimaryKeyReference();
			ReferencePrimaryKey pRef = new ReferencePrimaryKey(1, "java");
			obj.setId(pRef);
			obj.setEmail("java@java.com");
			obj.setCtime(new Date());
			obj.setUtime(new Date());

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

	public static void update() {
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			ReferencePrimaryKey ref = new ReferencePrimaryKey(1, "java");
			CompositePrimaryKeyReference obj = (CompositePrimaryKeyReference) session.get(CompositePrimaryKeyReference.class, ref);

			if (obj != null) {
				log.info("SUCCESS::" + obj.getEmail());

				obj.setEmail("vivek@java.com");
				session.update(obj);
			} else {
				log.info("FAIL::Object not found");
			}

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

class ReferencePrimaryKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String uname;

	public ReferencePrimaryKey() {
		super();
	}

	public ReferencePrimaryKey(int id, String uname) {
		super();
		this.id = id;
		this.uname = uname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (uname == null ? 0 : uname.hashCode());
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
		ReferencePrimaryKey other = (ReferencePrimaryKey) obj;
		if (id != other.id) {
			return false;
		}
		if (uname == null) {
			if (other.uname != null) {
				return false;
			}
		} else if (!uname.equals(other.uname)) {
			return false;
		}
		return true;
	}
}
