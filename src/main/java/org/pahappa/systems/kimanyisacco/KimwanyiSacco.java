package org.pahappa.systems.kimanyisacco;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;

public class KimwanyiSacco {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionConfiguration.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            // Hibernate will create the database tables based on the entity mappings
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SessionConfiguration.shutdown();
        }
    }
}
