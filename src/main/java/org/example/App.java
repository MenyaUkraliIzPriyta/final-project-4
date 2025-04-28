package org.example;

import org.example.entity.City;
import org.example.redis.CityCountry;
import org.example.repository.CityRepository;
import org.example.service.CityService;
import org.example.utils.SessionCreator;
import org.example.utils.TransactionManager;

import java.util.List;

public class App {
    public static void main( String[] args ) {
        SessionCreator sessionCreator = new SessionCreator();
        TransactionManager transactionManager = new TransactionManager(sessionCreator);
        CityRepository cityRepository = new CityRepository();
        CityService cityService = new CityService(cityRepository, transactionManager);

        List<City> cities = cityService.findAll();

        for (City city : cities) {
            System.out.println(city);
        }
        sessionCreator.shutdown();

    }
}
