package com.example.weather.web;

import com.example.weather.data.TestData;
import com.example.weather.service.WeatherService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.StringUtils.capitalize;


@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    private MockMvc mockMvc;


    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }


    @Test
    void getAllWeather() throws Exception {
        when(weatherService.getAllWeather()).thenReturn(TestData.getWeatherList());

        mockMvc.perform(get(WeatherController.WEATHER + WeatherController.GET_ALL_WEATHER))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].city")
                        .value(TestData.getWeather().getCity()))
                .andExpect(jsonPath("$.size()")
                        .value(1));
    }

    @Test
    void getCityName() throws Exception {
        when(weatherService.getWeatherByCity(capitalize(TestData.getWeather().getCity())))
                .thenReturn(TestData.getWeatherList());

        mockMvc.perform(get(WeatherController.WEATHER + WeatherController.GET_WEATHER_BY_CITY + "/espoo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].city")
                        .value(TestData.getWeather().getCity()))
                .andExpect(jsonPath("$.size()")
                        .value(1));

    }

    @Test
    void cityForecastByFrequency() throws Exception {
        when(weatherService.cityForecastByFrequency(capitalize(TestData.getWeather().getCity())))
                .thenReturn(TestData.getWeather());
        var resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post(WeatherController.WEATHER + WeatherController.POST_CITY_FORECAST_BY_FREQUENCY + "/espoo")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        String jsonWeather = resultActions.getResponse().getContentAsString();
        String actualCity = JsonPath.read(jsonWeather, "$.city");
        String actualCountry = JsonPath.read(jsonWeather, "$.country");

        assertEquals(TestData.getWeather().getCity(), actualCity);
        assertEquals(TestData.getWeather().getCountry(), actualCountry);
    }
}
