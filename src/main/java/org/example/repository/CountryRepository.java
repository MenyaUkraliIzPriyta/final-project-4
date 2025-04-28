package org.example.repository;

import org.example.entity.Country;
import org.hibernate.Session;

import java.util.List;

public class CountryRepository extends FoundationRepository<Country> {
    public CountryRepository() {
        super(Country.class);
    }
    @Override
    public List<Country> findAll(Session session) {
        return session.createQuery("select c from Country c join fetch c.languages", Country.class).list();
    }
}
