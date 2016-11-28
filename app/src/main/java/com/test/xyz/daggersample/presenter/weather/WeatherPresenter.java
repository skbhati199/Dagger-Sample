package com.test.xyz.daggersample.presenter.weather;

public interface WeatherPresenter extends OnWeatherInfoCompletedListener {
    void requestWeatherInformation();
}
