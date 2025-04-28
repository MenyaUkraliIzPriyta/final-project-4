package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entity.City;
import org.example.entity.Country;
import org.example.repository.CountryRepository;
import org.example.utils.TransactionManager;

import java.util.List;

@AllArgsConstructor
public class CountryService {

    private CountryRepository countryRepository;
    private TransactionManager txManager;

    public List<Country> findAll() {
        return txManager.execute(countryRepository::findAll);
    }
}
