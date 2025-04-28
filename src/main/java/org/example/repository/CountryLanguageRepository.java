package org.example.repository;

import org.example.entity.CountryLanguage;

public class CountryLanguageRepository extends FoundationRepository<CountryLanguage> {
    public CountryLanguageRepository() {
        super(CountryLanguage.class);
    }
}
