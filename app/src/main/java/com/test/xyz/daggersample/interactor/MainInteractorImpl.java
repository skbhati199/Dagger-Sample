package com.test.xyz.daggersample.interactor;

import android.support.annotation.VisibleForTesting;

import com.google.common.base.Strings;
import com.test.xyz.daggersample.R;
import com.test.xyz.daggersample.presenter.details.OnRepoDetailsCompletedListener;
import com.test.xyz.daggersample.presenter.list.OnRepoListCompletedListener;
import com.test.xyz.daggersample.presenter.main.OnWeatherInfoCompletedListener;
import com.test.xyz.daggersample.service.api.HelloService;
import com.test.xyz.daggersample.service.api.RepoListService;
import com.test.xyz.daggersample.service.api.WeatherService;
import com.test.xyz.daggersample.service.api.model.Repo;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainInteractorImpl implements MainInteractor {
    private static final String TAG = MainInteractorImpl.class.getName();

    @Inject
    HelloService helloService;

    @Inject
    WeatherService weatherService;

    @Inject
    RepoListService repoListService;

    @Inject
    public MainInteractorImpl() {
    }

    @VisibleForTesting
    MainInteractorImpl(HelloService helloService, WeatherService weatherService,
                       RepoListService repoListService) {

        this.helloService = helloService;
        this.weatherService = weatherService;
        this.repoListService = repoListService;
    }

    @Override
    public void getWeatherInformation(final String userName, final String cityName, final OnWeatherInfoCompletedListener listener) {
        final String greeting = helloService.greet(userName) + "\n";

        if (Strings.isNullOrEmpty(userName)) {
            listener.onUserNameValidationError(R.string.username_empty_message);
            return;
        }

        if (Strings.isNullOrEmpty(cityName)) {
            listener.onCityValidationError(R.string.city_empty_message);
            return;
        }

        weatherService.getWeatherInfo(cityName).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e.getMessage());
            }

            @Override
            public void onNext(Integer temperature) {
                String temp = "Current weather in " + cityName + " is " + temperature + "°F";

                listener.onSuccess(greeting + temp);
            }
        });
    }

    @Override
    public void getRepoList(final String userName, final OnRepoListCompletedListener listener) {
        if (Strings.isNullOrEmpty(userName)) {
            listener.onRepoListRetrievalFailure("Username must be provided!");
            return;
        }

        repoListService.getRepoList(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onRepoListRetrievalFailure("Unable to get repo items: " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Repo> repoList) {
                        listener.onRepoListRetrievalSuccess(repoList);
                    }
                });
    }

    @Override
    public void getRepoItemDetails(final String userName, final String projectID, final OnRepoDetailsCompletedListener listener) {
        if (Strings.isNullOrEmpty(userName)) {
            listener.onRepoDetailsRetrievalFailure("Username must be provided!");
            return;
        }

        repoListService.getRepoItemDetails(userName, projectID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Repo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onRepoDetailsRetrievalFailure("Unable to get repo item details: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Repo repo) {
                        listener.onRepoDetailsRetrievalSuccess(repo);
                    }
                });
    }
}
