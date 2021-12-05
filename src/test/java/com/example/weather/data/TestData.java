package com.example.weather.data;

import com.example.weather.model.FrequencyUnit;
import com.example.weather.model.Weather;

import java.net.URI;
import java.net.URISyntaxException;

public class TestData {
    public static String url = "https://api.openweathermap.org/data/2.5/weather?q=helsinki&units=metric&APPID=5536a9b0c84d081997982254c24fc53a";
    public static String jsonTestWeatherData =
    """
    {
   "coord":{
      "lon":24.9355,
      "lat":60.1695
   },
   "weather":[
      {
         "id":801,
         "main":"Clouds",
         "description":"few clouds",
         "icon":"02n"
      }
   ],
   "base":"stations",
   "main":{
      "temp":-17.15,
      "feels_like":-24.15,
      "temp_min":-17.79,
      "temp_max":-15.68,
      "pressure":1024,
      "humidity":70
   },
   "visibility":10000,
   "wind":{
      "speed":3.6,
      "deg":10
   },
   "clouds":{
      "all":20
   },
   "dt":1638727785,
   "sys":{
      "type":2,
      "id":2019088,
      "country":"FI",
      "sunrise":1638687872,
      "sunset":1638710255
   },
   "timezone":7200,
   "id":658225,
   "name":"Helsinki",
   "cod":200
   }
   """;


    public static Weather getCsvWeatherConfig() throws URISyntaxException {
        Weather csvWeatherConfig = new Weather();
        csvWeatherConfig.setFrequencyUnit(FrequencyUnit.SECOND.name());
        csvWeatherConfig.setFrequency(5);
        csvWeatherConfig.setUri(new URI(url));
        csvWeatherConfig.setTempLimit(3);
        return csvWeatherConfig;
    }



}
