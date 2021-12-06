package com.example.weather.data;

import com.example.weather.model.FrequencyUnit;
import com.example.weather.model.Weather;
import org.assertj.core.util.Lists;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class TestData {
    public static String url = "https://api.openweathermap.org/data/2.5/weather?q=espoo&units=metric&APPID=5536a9b0c84d081997982254c24fc53a";
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

    public static Weather getWeather() throws URISyntaxException {
        Weather weather = new Weather();
        weather.setCity("espoo");
        weather.setFrequencyUnit(FrequencyUnit.SECOND.name());
        weather.setFrequency(5);
        weather.setUri(new URI(url));
        weather.setTempLimit(3);
        weather.setMaxTemp(5.5);
        weather.setMinTemp(2.2);
        weather.setFeelsLike(3.3);
        return weather;
    }

    public static Optional<List<Weather>> getOptionalWeather() throws URISyntaxException {
        Optional<List<Weather>> optionalWeathers = Optional.of( Lists.list(getWeather()));
        return optionalWeathers;
    }

    public static List<Weather> getWeatherList() throws URISyntaxException {
        return Lists.list(getWeather());
    }

}
