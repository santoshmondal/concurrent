package in.async.hibernate.misc;

import in.async.hibernate.util.HibernateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CompositePrimaryKey implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(CompositePrimaryKey.class);

	private int id;
	private String fname;

	private String email;

	private Date utime;
	private Date ctime;

	public CompositePrimaryKey() {
		super();
	}

	public CompositePrimaryKey(String fname, String email, Date utime) {
		super();
		this.fname = fname;
		this.email = email;
		this.utime = utime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
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

			CompositePrimaryKey obj = new CompositePrimaryKey();
			obj.setId(2);
			obj.setFname("java");
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

			Query query = session
					.createQuery("FROM in.async.hibernate.misc.CompositePrimaryKey cpk Where cpk.id=:id and cpk.fname=:fname");
			query.setInteger("id", 2);
			query.setString("fname", "java");

			List<CompositePrimaryKey> list = query.list();
			if (list != null && list.size() != 0) {
				CompositePrimaryKey obj = list.get(0);
				log.info("LIST::" + obj.getEmail());
			} else {
				log.info("No Record Found");
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
