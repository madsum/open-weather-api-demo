package com.example.weather.service;

import com.example.weather.init.ConfigReader;
import com.example.weather.model.FrequencyUnit;
import com.example.weather.model.Weather;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.util.WeatherUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class WeatherService {

    private final WeatherRepository repository;

    public RestTemplate restTemplate() {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return new RestTemplate(factory);
    }

    public void insertWeather(Weather weather){
        repository.save(weather);
    }

    public List<Weather> getAllWeather(){
        return repository.findAll();
    }

    public List<Weather> getWeatherByCity(String cityName){
        var weathers = repository.findAllByCity(cityName);
        if(weathers.isPresent()){
           return weathers.get();
        }
        return null;
    }

    @Async
    public void cityForecastByFrequency(String cityName){
        var weathers = repository.findAllByCity(cityName);
        if(weathers.isPresent()){
            var foundWeathers = weathers.get();
            Weather lastCityWeather = foundWeathers.get(foundWeathers.size()-1);
            lastCityWeather.setTempLimit(ConfigReader.weathers.get(ConfigReader.weathers.size() -1).getTempLimit());
            lastCityWeather.setFrequency(ConfigReader.weathers.get(ConfigReader.weathers.size() -1).getFrequency());
            lastCityWeather.setFrequencyUnit(ConfigReader.weathers.get(ConfigReader.weathers.size() -1).getFrequencyUnit());
            callApi(lastCityWeather);
        }
    }

    @SneakyThrows
    public void callApi(Weather weather){
        RestTemplate restTemplate = restTemplate();
        CallableWeatherApi callableWeatherApi = new CallableWeatherApi(restTemplate, weather.getUri());
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        String unit = weather.getFrequencyUnit();
        FrequencyUnit frequencyUnit = FrequencyUnit.valueOf(FrequencyUnit.class, unit);
        long delay = weather.getFrequency();
        Future<String> resultFuture = null;
        switch (frequencyUnit){
            case SECOND:
                resultFuture = executorService.schedule(callableWeatherApi, delay, TimeUnit.SECONDS);
                break;
            case MINUTE:
                resultFuture = executorService.schedule(callableWeatherApi, delay, TimeUnit.MINUTES);
                break;
            case HOUR:
                resultFuture = executorService.schedule(callableWeatherApi, delay, TimeUnit.HOURS);
                break;
            case DAY:
                resultFuture = executorService.schedule(callableWeatherApi, delay, TimeUnit.DAYS);
                break;
            default:
                break;
        }
        String result = resultFuture.get();
        log.info(result);
        insertWeather( WeatherUtils.buildWeather(result, weather));
        executorService.shutdown();
    }

    public void fetchWeatherData(){
        RestTemplate restTemplate = restTemplate();
        ConfigReader.weathers.forEach(weather -> {
            var result = restTemplate.getForEntity(weather.getUri(), String.class);
            log.info(result.getBody());
            insertWeather( WeatherUtils.buildWeather(result.getBody(), weather));
        });
    }
}
