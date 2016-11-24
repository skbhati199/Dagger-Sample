package com.test.xyz.daggersample.interactor;

import com.test.xyz.daggersample.presenter.details.OnRepoDetailsCompletedListener;
import com.test.xyz.daggersample.presenter.list.OnRepoListCompletedListener;
import com.test.xyz.daggersample.presenter.main.OnWeatherInfoCompletedListener;

public interface MainInteractor {
    void getWeatherInformation(String userName, String cityName, final OnWeatherInfoCompletedListener listener);

    void getRepoList(String userName, final OnRepoListCompletedListener listener);

    void getRepoItemDetails(String userName, String projectID, final OnRepoDetailsCompletedListener listener);
}