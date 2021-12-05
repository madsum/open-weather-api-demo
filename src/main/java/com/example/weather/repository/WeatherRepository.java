package com.example.weather.repository;

import com.example.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Query("SELECT w FROM Weather w WHERE w.city LIKE :city")
    Optional<List<Weather>> findAllByCity(@Param("city") String city);

}
