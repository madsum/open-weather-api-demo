package com.example.weather.util;

import com.example.weather.OpenWeatherApiDemoApplication;
import com.example.weather.data.TestData;
import com.example.weather.model.Weather;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        classes = OpenWeatherApiDemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WeatherUtilsTest {

    @Autowired
    private Environment environment;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void buildWeather() throws URISyntaxException {
       Weather weather = WeatherUtils.buildWeather(TestData.jsonTestWeatherData,
                                                TestData.getWeather());
       assertNotNull(weather);
       weather.setId(1l);
       assertThat(weather).hasNoNullFieldsOrProperties();
    }

    @Test
    void buildOpenWeatherUri() {
      URI uri = WeatherUtils.buildOpenWeatherUri(environment, "Helsinki");
      assertNotNull(uri);
    }

    @Test
    void readCsvConfig(){
        InputStream resource = OpenWeatherApiDemoApplication.class.getResourceAsStream("/config.csv");
        System.out.println(resource.toString());
        List<Weather> actualWeathers = WeatherUtils.readCsvConfig(resource, environment);
        assertNotNull(actualWeathers);
        assertTrue(actualWeathers.size() == 1);
    }
}
