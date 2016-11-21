package com.test.xyz.daggersample.presenter.main;

public interface WeatherPresenter extends OnWeatherInfoCompletedListener {
    void requestWeatherInformation();
}
