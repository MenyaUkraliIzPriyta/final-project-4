package org.example.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Function;

public class TransactionManager {
    private final SessionCreator sessionCreator;

    public TransactionManager(SessionCreator sessionCreator) {
        this.sessionCreator = sessionCreator;
    }

    public <R> R execute(Function<Session, R> action) {
        Session session = sessionCreator.getSession();
        Transaction tx = session.beginTransaction();
        try {
            R result = action.apply(session);
            tx.commit();
            return result;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void execute(Runnable action) {
        execute(session -> {
            action.run();
            return null;
        });
    }
}
