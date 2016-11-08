package com.test.xyz.daggersample.service.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaTestRunner;
import rx.schedulers.Schedulers;

@RunWith(RxJavaTestRunner.class)
public class RepoListServiceTest {
    private static final String USER_NAME = "hazems";
    private static final String TAG = RepoListServiceTest.class.getSimpleName();

    private RepoListServiceManager repoListService;

    @Before
    public void setup() {
        repoListService = new RepoListServiceManager();

        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void retrieveRepoList_whenUserNameIsValid_shouldReturnRepoList() {
        try {
            Observable<List<String>> observable = repoListService.retrieveRepoList(USER_NAME);
            List<String> output = observable.toBlocking().first();

            Assert.assertTrue(output.size() >= 0);
        } catch (Exception e) {
            Assert.fail("Unable to get repos now !!!");
        }
    }
}
