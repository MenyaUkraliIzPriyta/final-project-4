package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entity.City;
import org.example.entity.CountryLanguage;
import org.example.repository.CityRepository;
import org.example.utils.TransactionManager;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class CityService {

    private CityRepository cityRepository;
    private TransactionManager txManager;

    public List<City> findAll() {
        return txManager.execute(cityRepository::findAll);
    }

    public void testMysqlData(List<Integer> ids) {
        txManager.execute(session -> {
            for (Integer id : ids) {
                City city = cityRepository.findById(session, id);  // Получаем город по ID
                Set<CountryLanguage> languages = city.getCountry().getLanguages();  // Извлекаем языки страны
                System.out.println("City: " + city.getName() + ", Languages: " + languages.size());
            }
            return null;
        });
    }
}
