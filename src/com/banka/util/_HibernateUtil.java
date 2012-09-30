package com.banka.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class _HibernateUtil {
	private static SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new AnnotationConfiguration()
					.addPackage("com.vizyon.arge.tirtil.entity")
					.addAnnotatedClass(com.banka.entity.Musteri.class)
					.configure().buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}