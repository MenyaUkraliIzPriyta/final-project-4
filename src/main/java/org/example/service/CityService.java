package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entity.City;
import org.example.repository.CityRepository;
import org.example.utils.TransactionManager;

import java.util.List;

@AllArgsConstructor
public class CityService {

    private CityRepository cityRepository;
    private TransactionManager txManager;

    public List<City> findAll() {
        return txManager.execute(cityRepository::findAll);
    }
}
