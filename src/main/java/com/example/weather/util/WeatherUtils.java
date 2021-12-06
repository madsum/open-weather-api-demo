package com.example.weather.util;

import com.example.weather.model.Weather;
import com.jayway.jsonpath.JsonPath;
import com.opencsv.CSVReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WeatherUtils {

    public static Weather buildWeather(String jsonWeather, Weather csvWeatherConfig) {
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
        log.info(weather.toString());
        return weather;
    }

    @SneakyThrows
    public static URI buildOpenWeatherUri(Environment environment, String cityName) {
        String url = environment.getProperty("openWeatherUrl");
        String apiKey = environment.getProperty("openWeatherApiKey");
        URI uri = new URI(url + "?q=" + cityName + "&units=metric&APPID=" + apiKey);
        return uri;
    }

    @SneakyThrows
    public static List<Weather> readCsvConfig(InputStream resource, Environment environment) {
        List<Weather> weathers = new ArrayList<>();
        try(CSVReader reader = new CSVReader(new InputStreamReader(resource))) {
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
                URI uri = buildOpenWeatherUri(environment, weather.getCity());
                weather.setUri(uri);
                weathers.add(weather);
            }
        }catch (Exception e){
            log.error("Exception message {}, Reason {}", e.getMessage(), e.getCause().toString());
        }
        return weathers;
    }
}
