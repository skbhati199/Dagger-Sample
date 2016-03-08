package com.test.xyz.daggersample1.service.di;

import android.app.Application;
import android.content.res.Resources;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    DaggerApplication app;

    public MainModule(DaggerApplication application) {
        app = application;
    }

    @Provides
    @Singleton
    protected Application provideApplication() {
        return app;
    }


    @Provides
    @Singleton
    protected Resources provideResources() {
        return app.getResources();
    }
}
