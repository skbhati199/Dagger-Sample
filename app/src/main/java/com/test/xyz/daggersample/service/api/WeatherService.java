package com.test.xyz.daggersample.service.api;

import rx.Observable;

public interface WeatherService {
    Observable<Integer> getWeatherInfo(String city);
}
