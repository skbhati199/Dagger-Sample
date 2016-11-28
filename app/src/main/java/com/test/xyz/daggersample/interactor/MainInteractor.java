package com.test.xyz.daggersample.interactor;

import com.test.xyz.daggersample.presenter.repodetails.OnRepoDetailsCompletedListener;
import com.test.xyz.daggersample.presenter.repolist.OnRepoListCompletedListener;
import com.test.xyz.daggersample.presenter.weather.OnWeatherInfoCompletedListener;

public interface MainInteractor {
    void getWeatherInformation(String userName, String cityName, final OnWeatherInfoCompletedListener listener);

    void getRepoList(String userName, final OnRepoListCompletedListener listener);

    void getRepoItemDetails(String userName, String projectID, final OnRepoDetailsCompletedListener listener);
}