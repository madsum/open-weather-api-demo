package com.example.weather.web;

import com.example.weather.model.Weather;
import com.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.StringUtils.capitalize;

@RestController
@RequestMapping(WeatherController.WEATHER)
public class WeatherController {

    public static final String WEATHER = "/weather";
    public static final String GET_ALL_WEATHER = "/all";
    public static final String GET_WEATHER_BY_CITY = "/city";
    public static final String POST_CITY_FORECAST_BY_FREQUENCY = "/cityByFrequency";

    @Autowired
    private WeatherService weatherService;

    @GetMapping(value = GET_ALL_WEATHER)
    public List<Weather> getAllWeather() {
        return weatherService.getAllWeather();
    }

    @GetMapping(value = GET_WEATHER_BY_CITY+"/{cityName}")
    public List<Weather> getCityName(@PathVariable String cityName){
       return weatherService.getWeatherByCity(capitalize(cityName));
    }

    @PostMapping(value = POST_CITY_FORECAST_BY_FREQUENCY+"/{cityName}")
    public Weather cityForecastByFrequency(@PathVariable("cityName") String cityName){
       return weatherService.cityForecastByFrequency(capitalize(cityName));
    }

}
