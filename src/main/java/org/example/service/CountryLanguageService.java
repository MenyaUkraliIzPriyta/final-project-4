package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entity.CountryLanguage;
import org.example.repository.CountryLanguageRepository;
import org.example.utils.TransactionManager;

import java.util.List;

@AllArgsConstructor
public class CountryLanguageService {

    private CountryLanguageRepository countryLanguageRepository;
    private TransactionManager txManager;

    public List<CountryLanguage> findAll() {
        return txManager.execute(countryLanguageRepository::findAll);
    }
}
