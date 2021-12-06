package com.example.weather.service;

import com.example.weather.data.TestData;
import com.example.weather.model.Weather;
import com.example.weather.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

   @InjectMocks
   private  WeatherService weatherService;

   @Mock
   private WeatherRepository repository;


    @Test
    void callApi() throws URISyntaxException {
        Weather weather = TestData.getWeather();
        lenient().when(repository.save(any())).thenReturn(any());
        weatherService.callApi(weather);
        verify(repository, times(1)).save(any());
    }

    @Test
    void getWeatherByCity() throws URISyntaxException {
        Weather weather = TestData.getWeather();
        lenient().when(repository.findAllByCity(weather.getCity())).thenReturn(TestData.getOptionalWeather());

        var weatherByCity =  weatherService.getWeatherByCity(TestData.getWeather().getCity());
       assertNotNull(weatherByCity);
       assertSame(weatherByCity.size(), 1);
    }

}
