package org.example.utils;

import org.example.entity.City;
import org.example.entity.Country;
import org.example.entity.CountryLanguage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class SessionCreator {
    private final SessionFactory sessionFactory;

    public SessionCreator() {
        this.sessionFactory = buildSessionFactory();
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    private SessionFactory buildSessionFactory() {
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));

            return new Configuration()
                    .setProperties(properties)
                    .addAnnotatedClass(City.class)
                    .addAnnotatedClass(Country.class)
                    .addAnnotatedClass(CountryLanguage.class)
                    .buildSessionFactory();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            System.out.println("Closing SessionFactory...");
            sessionFactory.close();
        }
    }
}
