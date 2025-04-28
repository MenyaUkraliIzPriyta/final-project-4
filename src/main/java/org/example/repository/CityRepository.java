package org.example.repository;

import org.example.entity.City;

public class CityRepository extends FoundationRepository<City> {

    public CityRepository() {
        super(City.class);
    }
}
