package com.test.xyz.daggersample1.service;

import android.support.v7.appcompat.BuildConfig;

import com.test.xyz.daggersample1.di.ServiceModule;
import com.test.xyz.daggersample1.service.api.HelloService;
import com.test.xyz.daggersample1.service.api.RepoListService;
import com.test.xyz.daggersample1.service.api.WeatherService;
import com.test.xyz.daggersample1.service.exception.InvalidCityException;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import rx.Observer;

@RunWith(RobolectricTestRunner.class)
public class ServicesTest {
    private static String USER_NAME = "hazems";
    private static String GREET_PREFIX = "Hello ";
    private static String CITY = "NYC, USA";

    @Inject
    HelloService helloService;

    @Inject
    WeatherService weatherService;

    @Inject
    RepoListService repoListService;

    @Before
    public void setup() {
        TestComponent testComponent = DaggerServicesTest_TestComponent.builder()
                                                                       .serviceModule(new ServiceModule())
                                                                       .build();
        testComponent.inject(this);
    }

    @Test
    public void testHelloService() {
        String result = helloService.greet(USER_NAME);

        Assert.assertThat(result, CoreMatchers.containsString(GREET_PREFIX + USER_NAME));
    }

    @Test
    public void testWeatherService() {
        try {
            int temp = weatherService.getWeatherInfo(CITY);
        } catch (InvalidCityException e) {
            Assert.fail("Unable to get weather now !!!");
        }
    }

    @Test
    public void testRepoListService() {
        try {
            repoListService.retrieveRepoList("hazems").subscribe(new Observer<List<String>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    Assert.fail("Unable to get repos now !!!");
                }

                @Override
                public void onNext(List<String> strings) {
                    Assert.assertTrue(strings.size() >= 0);
                }
            });

        } catch (Exception e) {
            Assert.fail("Unable to get repos now !!!");
        }
    }

    @Singleton
    @Component(modules = {ServiceModule.class})
    static public interface TestComponent {
        void inject(ServicesTest test);
    }
}
