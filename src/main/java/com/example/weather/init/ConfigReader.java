package com.example.weather.init;

import com.example.weather.OpenWeatherApiDemoApplication;
import com.example.weather.model.Weather;
import com.example.weather.service.WeatherService;
import com.example.weather.util.WeatherUtils;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class ConfigReader {

    private final Environment env;
    private final WeatherService weatherService;
    public static  List<Weather> weathers = new ArrayList<>();

    public ConfigReader(Environment env, WeatherService weatherService) {
        this.env = env;
        this.weatherService = weatherService;
    }

    @PostConstruct
    public void readConfig() {
        try (InputStream resource = OpenWeatherApiDemoApplication.class.getResourceAsStream("/config.csv")) {
            CSVReader reader = new CSVReader(new InputStreamReader(resource));
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                Weather weather = new Weather();
                if (!lineInArray[0].contains("city")) {
                    weather.setCity(lineInArray[0]);
                } else {
                    continue;
                }

                if (!lineInArray[1].contains("temperaturesLimit")) {
                    weather.setTempLimit(Float.parseFloat(lineInArray[1]));
                }
                if (!lineInArray[2].contains("frequency")) {
                    weather.setFrequency(Integer.parseInt(lineInArray[2]));
                }
                if (!lineInArray[3].contains("unit")) {
                    weather.setFrequencyUnit(lineInArray[3].toUpperCase());
                }

                URI uri = WeatherUtils.buildOpenWeatherUri(env, weather.getCity());
                weather.setUri(uri);
                weathers.add(weather);
            }
        } catch (IOException | CsvValidationException e) {
            log.error("Error message: {}, Because {}",e.getMessage(), e.getCause().toString() );
        }
        weatherService.fetchWeatherData();
    }
}
