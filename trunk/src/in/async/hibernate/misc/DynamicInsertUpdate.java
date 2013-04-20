package in.async.hibernate.misc;

import in.async.hibernate.util.HibernateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class DynamicInsertUpdate implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DynamicInsertUpdate.class);

	private int id;
	private String fname;
	private String address;

	private Date utime;
	private Date ctime;

	public DynamicInsertUpdate() {
		super();
	}

	public DynamicInsertUpdate(String fname, String address, Date utime, Date ctime) {
		super();
		this.fname = fname;
		this.address = address;
		this.utime = utime;
		this.ctime = ctime;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	// Below this Main method and Business methods.
	public static void main(String args[]) {
		log.info("Main method execution starts::");
		dynamicUpdate1();
	}

	/**
	 * In HBM file when dynamic insert is true. Debug the SQL Query for better understanding.
	 * The hibernate does not consider those column whose value can be accepted null in table.
	 * In depth if the bean value is null for the property its not passed to db. 
	 * 
	 * CASE2::TRUE::It also helps to take the db default value specified.
	 * 
	 * IF FALSE::
	 * Insert done for all the mapping property with all the columns. 
	 */
	public static void dynamicInsert() {
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			DynamicInsertUpdate obj = new DynamicInsertUpdate();
			obj.setFname("Java");
			obj.setUtime(new Date());

			//session.save(obj);
			session.saveOrUpdate(obj);

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

	/**
	 * DynamicUpdate::TURE::Means update the non null value and only if the 
	 * data has been changed. 
	 */
	public static void dynamicUpdate() {
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			DynamicInsertUpdate obj = (DynamicInsertUpdate) session.load(DynamicInsertUpdate.class, 1);
			if (obj != null) {
				log.info("Success::Object found in db.");
				log.info("Object values::" + obj.getFname() + "," + obj.getAddress());

				// obj.setFname("Java Programmerr.");
				session.saveOrUpdate(obj);
			} else {
				log.info("Failure::Object not found in db.");
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

	public static void dynamicUpdate1() {
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			Query query = session.createQuery("FROM in.async.hibernate.misc.DynamicInsertUpdate");
			List<DynamicInsertUpdate> list = query.list();
			if (list.size() != 0) {
				DynamicInsertUpdate obj = list.get(0);
				session.saveOrUpdate(obj);
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
