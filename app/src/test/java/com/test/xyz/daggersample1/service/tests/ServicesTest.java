package com.test.xyz.daggersample1.service.tests;

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

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

@RunWith(RobolectricTestRunner.class)
public class ServicesTest {
    public static String USER_NAME = "hazems";
    public static String GREET_PREFIX = "Hello ";
    public static String CITY = "Cairo, Egypt";

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

    @After
    public void teardown() {
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
            String[] repoList = repoListService.retrieveRepoList("hazems");

            Assert.assertTrue(repoList.length >= 0);
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
