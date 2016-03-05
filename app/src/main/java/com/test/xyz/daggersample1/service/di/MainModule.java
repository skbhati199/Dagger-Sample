package com.test.xyz.daggersample1.service.di;

import android.app.Application;

import com.test.xyz.daggersample1.service.api.HelloService;
import com.test.xyz.daggersample1.service.impl.HelloServiceManager;
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
    HelloService provideHelloService() {
        return new HelloServiceManager();
    }
}
