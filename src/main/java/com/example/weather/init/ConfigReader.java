package com.example.weather.init;

import com.example.weather.OpenWeatherApiDemoApplication;
import com.example.weather.model.Weather;
import com.example.weather.service.WeatherService;
import com.example.weather.util.WeatherUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class ConfigReader {

    private final Environment environment;
    private final WeatherService weatherService;
    public static List<Weather> weathers = new ArrayList<>();

    public ConfigReader(Environment environment, WeatherService weatherService) {
        this.environment = environment;
        this.weatherService = weatherService;
    }

    @PostConstruct
    public void readConfig() {
        try (InputStream resource = OpenWeatherApiDemoApplication.class.getResourceAsStream("/config.csv")) {
            weathers = WeatherUtils.readCsvConfig(resource, environment);
        } catch (IOException e) {
            log.error("Error message: {}, Reason {}",e.getMessage(), e.getCause().toString() );
        }
        weatherService.fetchWeatherData();
    }
}
