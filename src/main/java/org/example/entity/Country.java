package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.Continent;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name= "country")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int default 0")
    private Integer countryId;

    @Column(name = "code", length = 3)
    private String code;

    @Column(name = "code_2", length = 2)
    private String code_2;

    @Column(name = "name", length = 52)
    private String name;

    @Column(name = "continent")
    @Enumerated(EnumType.ORDINAL)
    private Continent continent;

    @Column(name = "region", length = 28)
    private String region;

    @Column(name = "surface_area", precision = 10, scale = 2, nullable = false)
    private BigDecimal surfaceArea = BigDecimal.ZERO;

    @Column(name = "indep_year")
    private Short indepYear;

    @Column(name = "population", columnDefinition = "int default 0")
    private Integer population;

    @Column(name = "life_expectancy", precision = 3, scale = 1)
    private BigDecimal lifeExpectancy;

    @Column(name = "gnp", precision = 10, scale = 2)
    private BigDecimal GNP;

    @Column(name = "gnpo_id", precision = 10, scale = 2)
    private BigDecimal GNPOId;

    @Column(name = "local_name", length = 45)
    private String localName;

    @Column(name = "government_form", length = 45)
    private String governmentForm;

    @Column(name = "head_of_state", length = 60)
    private String headOfState;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capital")
    private City capitalCity;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Set<CountryLanguage> languages;










}
