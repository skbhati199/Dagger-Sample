package com.test.xyz.daggersample.di.module;

import com.test.xyz.daggersample.service.api.RepoListService;
import com.test.xyz.daggersample.service.api.WeatherService;
import com.test.xyz.daggersample.service.impl.WeatherServiceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class CommonModule {

    @Provides
    @Singleton
    WeatherService provideWeatherService() {
        return new WeatherServiceManager();
    }

    @Provides
    @Singleton
    RepoListService provideRepoListService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RepoListService.HTTPS_API_GITHUB_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RepoListService.class);
    }
}
