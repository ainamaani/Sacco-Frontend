package org.pahappa.systems.kimanyisacco.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.pahappa.systems.kimanyisacco.models.Member;

public class SessionConfiguration {
    private final static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml"); // Loads hibernate.cfg.xml configuration

            // Add annotated classes here
            configuration.addAnnotatedClass(Member.class);
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
