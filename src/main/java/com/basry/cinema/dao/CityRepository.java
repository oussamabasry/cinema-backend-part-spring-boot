package com.basry.cinema.dao;

import com.basry.cinema.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CityRepository extends JpaRepository<City, Long> {
}
