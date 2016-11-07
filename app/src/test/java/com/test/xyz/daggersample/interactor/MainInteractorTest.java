package com.test.xyz.daggersample.interactor;

import com.test.xyz.daggersample.presenter.details.OnRepoDetailsCompletedListener;
import com.test.xyz.daggersample.presenter.list.OnRepoListCompletedListener;
import com.test.xyz.daggersample.presenter.main.OnInfoCompletedListener;
import com.test.xyz.daggersample.service.api.HelloService;
import com.test.xyz.daggersample.service.api.RepoListService;
import com.test.xyz.daggersample.service.api.WeatherService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainInteractorTest {
    private static final String USER_NAME = "hazems";
    private static final String CITY = "New York, USA";
    private static final String PROJECT_ID = "Test";

    @Mock
    private OnInfoCompletedListener onInfoCompletedListener;

    @Mock
    private OnRepoListCompletedListener onRepoListCompletedListener;

    @Mock
    private OnRepoDetailsCompletedListener onRepoDetailsCompletedListener;

    @Mock
    private HelloService helloService;

    @Mock
    private WeatherService weatherService;

    @Mock
    private RepoListService repoListService;

    private MainInteractor mainInteractor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainInteractor = new MainInteractorImpl(helloService, weatherService, repoListService);
    }

    @Test
    public void testGetInformation() {
        try {
            //GIVEN
            when(helloService.greet(anyString())).thenReturn("Hi man!");
            when(weatherService.getWeatherInfo(anyString())).thenReturn(10);

            //WHEN
            mainInteractor.getInformation(USER_NAME, CITY, onInfoCompletedListener);

            //THEN
            verify(onInfoCompletedListener, timeout(50)).onSuccess(any(String.class));
        } catch (Exception exception) {
            fail("Unable to getWeatherInfo !!!");
        }
    }

    @Test
    public void testGetRepoList() throws Exception {
        //GIVEN
        List<String> result = new ArrayList<>();
        Observable<List<String>> observable = Observable.just(result);
        when(repoListService.retrieveRepoList(anyString())).thenReturn(observable);

        //WHEN
        mainInteractor.getRepoList(USER_NAME, onRepoListCompletedListener);

        //THEN
        verify(onRepoListCompletedListener).onRepoListRetrievalSuccess(result);
    }

    @Test
    public void testGetRepoItemDetails() throws Exception {
        //GIVEN
        String result = "result";
        Observable<String> observable = Observable.just(result);
        when(repoListService.retrieveRepoItemDetails(anyString(), anyString())).thenReturn(observable);

        // WHEN
        mainInteractor.getRepoItemDetails(USER_NAME, PROJECT_ID, onRepoDetailsCompletedListener);

        //THEN
        verify(onRepoDetailsCompletedListener).onRepoDetailsRetrievalSuccess(result);
    }
}
