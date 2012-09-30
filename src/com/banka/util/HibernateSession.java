package com.banka.util;

import org.hibernate.*;
import org.hibernate.cfg.*;

import java.util.Properties;

public class HibernateSession {

	public static Properties properties = null;

	private static SessionFactory sessionFactory = null;

	private static final ThreadLocal threadSession = new ThreadLocal();

	private static final ThreadLocal threadTransaction = new ThreadLocal();

	static {
		Configuration c = new AnnotationConfiguration()
				.addPackage("com.banka.entity")
				.addAnnotatedClass(com.banka.entity.Musteri.class)
				.addAnnotatedClass(com.banka.entity.Hesap.class)
				.addAnnotatedClass(com.banka.entity.Sube.class)
				.addAnnotatedClass(com.banka.entity.BankaMemuru.class)
				.addAnnotatedClass(com.banka.entity.Islem.class)
				.addAnnotatedClass(com.banka.entity.IslemUcreti.class)				
				.configure();

		properties = c.getProperties();
		sessionFactory = c.buildSessionFactory();
	}

	public static Properties getProperties() {
		return properties;
	}

	// Eðer session açýlmamýþsa yeni bir session açar.
	public static Session openSession() {
		Session s = (Session) threadSession.get();
		if (s == null) {
			s = sessionFactory.openSession();
			threadSession.set(s);
		}
		return s;
	}

	// Açýk olan session'ý kapatýr.
	public static void closeSession() {
		Session s = (Session) threadSession.get();
		threadSession.set(null);
		if (s != null && s.isOpen())
			s.close();
	}

	// Okuma , yazma ve silme iþlemleri için transaction baþlatýr.
	public static void beginTransaction() {
		Transaction trsc = (Transaction) threadTransaction.get();
		if (trsc == null) {
			trsc = openSession().beginTransaction();
			threadTransaction.set(trsc);
		}
	}

	// Okuma , yazma ve silme iþlemleri bittiðinde transaction'ý bitirir.
	public static void commitTransaction() {
		Transaction tx = (Transaction) threadTransaction.get();
		if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack())
			tx.commit();
		threadTransaction.set(null);
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
