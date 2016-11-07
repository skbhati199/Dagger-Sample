package com.test.xyz.daggersample.di;

import com.test.xyz.daggersample.di.module.CommonModule;
import com.test.xyz.daggersample.service.api.HelloService;
import com.test.xyz.daggersample.service.impl.HelloServiceReleaseManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = CommonModule.class)
public class ServiceModule {

    @Provides
    @Singleton
    HelloService provideHelloService() {
        return new HelloServiceReleaseManager();
    }

}
