package in.async.hibernate.misc;

import in.async.hibernate.util.HibernateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

class MiscMapStringBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Map<String, String> map;
	private Date ctime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
}

public class MiscMapStringDemo {
	private static final Logger log = Logger.getLogger(MiscMapStringDemo.class);

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

			MiscMapStringBean obj = new MiscMapStringBean();
			Map<String, String> map = new HashMap<String, String>();
			map.put("KEY1", "VALUE1");
			map.put("KEY2", "VALUE2");
			map.put("KEY3", "VALUE3");

			obj.setCtime(new Date());
			obj.setMap(map);

			session.save(obj);

			txn.commit();

			// load a record now
			MiscMapStringBean load = (MiscMapStringBean) session.load(MiscMapStringBean.class, 1);
			System.out.println(load.getMap());
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
