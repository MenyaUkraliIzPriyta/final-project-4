package org.example.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@AllArgsConstructor
public class FoundationRepository<T> {
    private final Class<T> entityClass;

    public void save(Session session, T entity) {
        session.persist(entity);
    }

    public void delete(Session session, T entity) {
        session.remove(entity);
    }

    public T findById(Session session, Long id) {
        return session.get(entityClass, id);
    }

    public List<T> findAll(Session session) {
        return session.createQuery("from " + entityClass.getName(), entityClass).list();
    }

    public void update(Session session, T entity) {
        session.merge(entity);
    }

    public <T> List<T> createQueryList(Session session, Class<T> clazz, String request) {
        return session.createQuery(request, clazz).list();
    }
}
