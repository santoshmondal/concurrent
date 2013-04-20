package in.async.hibernate.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	private static final Logger log = Logger.getLogger(HibernateUtil.class);
	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			log.error("Initial SessionFactory creation failed." + ex.getMessage(), ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void main(String args[]) {
		SessionFactory sFactory = HibernateUtil.getSessionFactory();
		if (sFactory != null) {
			log.info("Success::Hibernate Configuration.");
		} else {
			log.info("Failure::Hibernate Configuration.");
		}
	}
}
