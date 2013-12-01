package in.async.hibernate.crud;

import in.async.hibernate.util.HibernateUtil;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

class BasicCRUDBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private BasicPK id;
	private String email;
	private Date ctime;

	public BasicCRUDBean() {
		super();
	}

	public BasicCRUDBean(BasicPK id, String email, Date ctime) {
		super();
		this.id = id;
		this.email = email;
		this.ctime = ctime;
	}

	public BasicPK getId() {
		return id;
	}

	public void setId(BasicPK id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}

class BasicPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String uname;

	public BasicPK() {
		super();
	}

	public BasicPK(int id, String uname) {
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
		BasicPK other = (BasicPK) obj;
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

public class BasicCRUD {
	private static final Logger log = Logger.getLogger(BasicCRUD.class);

	public static boolean insert(BasicCRUDBean obj) {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			session.save(obj);
			txn.commit();
			result = true;
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

		return result;
	}

	public static boolean update(BasicCRUDBean obj) {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			session.saveOrUpdate(obj);
			txn.commit();
			result = true;
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

		return result;
	}

	public static BasicCRUDBean find(BasicPK pk) {
		BasicCRUDBean obj = null;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			obj = (BasicCRUDBean) session.get(BasicCRUDBean.class, pk);
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

		return obj;
	}
}
