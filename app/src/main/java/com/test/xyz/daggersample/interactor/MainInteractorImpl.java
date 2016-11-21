package com.test.xyz.daggersample.interactor;

import android.support.annotation.VisibleForTesting;

import com.test.xyz.daggersample.presenter.details.OnRepoDetailsCompletedListener;
import com.test.xyz.daggersample.presenter.list.OnRepoListCompletedListener;
import com.test.xyz.daggersample.presenter.main.OnWeatherInfoCompletedListener;
import com.test.xyz.daggersample.service.api.HelloService;
import com.test.xyz.daggersample.service.api.RepoListService;
import com.test.xyz.daggersample.service.api.WeatherService;
import com.test.xyz.daggersample.service.exception.InvalidCityException;
import com.test.xyz.daggersample.R;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;

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

    @VisibleForTesting MainInteractorImpl(HelloService helloService, WeatherService weatherService, RepoListService repoListService) {
        this.helloService = helloService;
        this.weatherService = weatherService;
        this.repoListService = repoListService;
    }

    @Override
    public void getWeatherInformation(final String userName, final String cityName, final OnWeatherInfoCompletedListener listener) {
        final String greeting = helloService.greet(userName) + "\n";

        if (userName != null && userName.trim().equals("")) {
            listener.onUserNameValidationError(R.string.username_empty_message);
            return;
        }

        if (cityName != null && cityName.trim().equals("")) {
            listener.onCityValidationError(R.string.city_empty_message);
            return;
        }

        //TODO refactor to RX Android ...
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    int temperature = weatherService.getWeatherInfo(cityName);
                    String temp = "Current weather in " + cityName + " is " + temperature + "°F";

                    listener.onSuccess(greeting + temp);
                } catch (InvalidCityException ex) {
                    listener.onFailure(ex.getMessage());
                } catch (Exception ex) {
                    listener.onFailure("Unable to get weather information");
                }
            }
        });

        thread.start();
    }

    @Override
    public void getRepoList(final String userName, final OnRepoListCompletedListener listener) {
        if (userName.equals("")) {
            listener.onRepoListRetrievalFailure("Username must be provided!");
            return;
        }

        repoListService.retrieveRepoList(userName).subscribe(new Observer<List<String>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.onRepoListRetrievalFailure("Unable to get repo items: " + e.getMessage());
            }

            @Override
            public void onNext(List<String> values) {
                listener.onRepoListRetrievalSuccess(values);
            }
        });
    }

    @Override
    public void getRepoItemDetails(final String userName, final String projectID, final OnRepoDetailsCompletedListener listener) {
        if (userName.equals("")) {
            listener.onRepoDetailsRetrievalFailure("Username must be provided!");
            return;
        }

        repoListService.retrieveRepoItemDetails(userName, projectID).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.onRepoDetailsRetrievalFailure("Unable to get repo item details: " + e.getMessage());
            }

            @Override
            public void onNext(String value) {
                listener.onRepoDetailsRetrievalSuccess(value);
            }
        });
    }
}
