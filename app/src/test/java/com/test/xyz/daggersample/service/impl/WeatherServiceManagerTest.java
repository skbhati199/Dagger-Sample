package com.test.xyz.daggersample.service.impl;

import com.test.xyz.daggersample.service.api.WeatherService;
import com.test.xyz.daggersample.service.exception.InvalidCityException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceManagerTest {
    private static String CITY = "NYC, USA";

    private WeatherService weatherService;

    @Before
    public void setup() {
        weatherService = new WeatherServiceManager();
    }

    @Test
    public void testWeatherService() {
        try {
            weatherService.getWeatherInfo(CITY);
        } catch (InvalidCityException e) {
            Assert.fail("Unable to get weather now !!!");
        }
    }
}
