package org.example;

import org.example.entity.City;
import org.example.entity.Country;
import org.example.entity.CountryLanguage;
import org.example.redis.CityCountry;
import org.example.redis.Language;
import org.example.redis.RedisHelper;
import org.example.repository.CityRepository;
import org.example.service.CityService;
import org.example.utils.SessionCreator;
import org.example.utils.TransactionManager;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class App {

    public static void main( String[] args ) {
        SessionCreator sessionCreator = new SessionCreator();
        TransactionManager transactionManager = new TransactionManager(sessionCreator);
        CityRepository cityRepository = new CityRepository();
        CityService cityService = new CityService(cityRepository, transactionManager);


        List<Integer> ids = Arrays.asList(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);


        List<City> cities = cityService.findAll();
        List<CityCountry> preparedData = transformData(cities);
        try (RedisHelper redis = new RedisHelper("localhost", 6379)) {
            redis.pushToRedis(preparedData);
        }
        sessionCreator.getSession().close();

        long startRedis = System.currentTimeMillis();
        try (RedisHelper redis = new RedisHelper("localhost", 6379)) {
            redis.testRedisData(ids);
        }
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        cityService.testMysqlData(ids);
        long stopMysql = System.currentTimeMillis();


        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));

        sessionCreator.shutdown();

    }

    private static List<CityCountry> transformData(List<City> cities) {
        return cities.stream().map(city -> {
            CityCountry res = new CityCountry();
            res.setId(city.getCityId());
            res.setName(city.getName());
            res.setPopulation(city.getPopulation());
            res.setDistrict(city.getDistrict());

            Country country = city.getCountry();
            res.setAlternativeCountryCode(country.getCode_2());
            res.setContinent(country.getContinent());
            res.setCountryCode(country.getCode());
            res.setCountryName(country.getName());
            res.setCountryPopulation(country.getPopulation());
            res.setCountryRegion(country.getRegion());
            res.setCountrySurfaceArea(country.getSurfaceArea());
            Set<CountryLanguage> countryLanguages = country.getLanguages();
            Set<Language> languages = countryLanguages.stream().map(cl -> {
                Language language = new Language();
                language.setLanguage(cl.getLanguage());
                language.setIsOfficial(cl.getIsOfficial());
                language.setPercentage(cl.getPercentage());
                return language;
            }).collect(Collectors.toSet());
            res.setLanguages(languages);

            return res;
        }).collect(Collectors.toList());
    }
}
