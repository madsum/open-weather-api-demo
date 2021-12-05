package com.example.weather.util;

import com.example.weather.model.Weather;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;

import java.net.URI;

public class WeatherUtils {

    public static Weather buildWeather(String jsonWeather, Weather csvWeatherConfig){
        String city = JsonPath.read(jsonWeather, "$.name");
        String country = JsonPath.read(jsonWeather, "$.sys.country");
        String description = JsonPath.read(jsonWeather, "$.weather[0].description");
        double currentTemp = JsonPath.read(jsonWeather, "$.main.temp");
        double feelsLike = JsonPath.read(jsonWeather, "$.main.feels_like");
        double minTemp = JsonPath.read(jsonWeather, "$.main.temp_min");
        double maxTemp = JsonPath.read(jsonWeather, "$.main.temp_max");

        Weather weather = new Weather.Builder()
                .city(city)
                .country(country)
                .currentTemp(currentTemp)
                .description(description)
                .feelsLike(feelsLike)
                .frequency(csvWeatherConfig.getFrequency())
                .frequencyUnit(csvWeatherConfig.getFrequencyUnit())
                .maxTemp(maxTemp)
                .minTemp(minTemp)
                .tempLimit(csvWeatherConfig.getTempLimit())
                .uri(csvWeatherConfig.getUri())
                .build();
        System.out.println("stop");
        return weather;
    }

    @SneakyThrows
    static public URI buildOpenWeatherUri(Environment environment, String cityName){
        String url = environment.getProperty("openWeatherUrl");
        String apiKey = environment.getProperty("openWeatherApiKey");
        URI uri = new URI(url + "?q=" + cityName + "&units=metric&APPID=" + apiKey);
        return uri;
    }
}
