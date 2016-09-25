package com.test.xyz.daggersample1.ui.presenter.tests;

import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample1.di.DaggerApplication;
import com.test.xyz.daggersample1.di.ServiceModule;
import com.test.xyz.daggersample1.di.component.AppComponent;
import com.test.xyz.daggersample1.di.module.AppModule;
import com.test.xyz.daggersample1.di.scope.ActivityScope;
import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.ui.fragment.main.MainFragmentComponent;
import com.test.xyz.daggersample1.ui.fragment.main.MainView;
import com.test.xyz.daggersample1.ui.presenter.main.MainPresenter;
import com.test.xyz.daggersample1.ui.presenter.main.MainPresenterImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(application = DaggerApplication.class)
public class MainPresenterTest {
    private static final String USER_NAME = "hazems";
    private static final String CITY = "New York, USA";

    static final String MOCK_INFO_SUCCESS_MSG = "MOCK INFO SUCCESS MSG";

    @Inject
    MainPresenter mainPresenter;

    MainView mainView;

    @Before
    public void setup() {
        DaggerApplication daggerApplication = (DaggerApplication) RuntimeEnvironment.application;

        TestAppComponent testAppComponent = DaggerMainPresenterTest_TestAppComponent.builder()
                .appModule(new AppModule(daggerApplication))
                .build();

        mainView = mock(MainView.class);

        MainActivityTestComponent mainActivityTestComponent = testAppComponent.add(new MainActivityMockModule(mainView));

        mainActivityTestComponent.inject(this);
    }

    @Test
    public void testGetValidInformation() throws Exception {

        // Here we need to set the view to some data in order to test the main presenter ...
        when(mainView.getUserNameText()).thenReturn(USER_NAME);
        when(mainView.getCityText()).thenReturn(CITY);

        mainPresenter.requestInformation();

        verify(mainView, times(1)).hideBusyIndicator();
        verify(mainView, times(1)).showResult(MOCK_INFO_SUCCESS_MSG);
    }

    @Test
    public void testGetInformationForEmptyUser() throws Exception {

        // Here we need to set the view to some data in order to test the main presenter ...
        when(mainView.getUserNameText()).thenReturn("");
        when(mainView.getCityText()).thenReturn(CITY);

        mainPresenter.requestInformation();

        verify(mainView, times(1)).hideBusyIndicator();
        verify(mainView, times(1)).showUserNameError(R.string.username_invalid_message);
        verify(mainView, never()).showResult(any(String.class));
    }

    @Test
    public void testGetInformationForEmptyCity() throws Exception {

        // Here we need to set the view to some data in order to test the main presenter ...
        when(mainView.getUserNameText()).thenReturn(USER_NAME);
        when(mainView.getCityText()).thenReturn("");

        mainPresenter.requestInformation();

        verify(mainView, times(1)).hideBusyIndicator();
        verify(mainView, times(1)).showCityNameError(R.string.city_invalid_message);
        verify(mainView, never()).showResult(any(String.class));
    }

    /**
     * Mock Activity Module to provide mocked MainInteractorMockImpl's implementation ...
     */
    @Module
    public class MainActivityMockModule {

        public final MainView view;

        public MainActivityMockModule(MainView view) {
            this.view = view;
        }

        @Provides
        @ActivityScope
        MainView provideMainView() {
            return this.view;
        }

        @Provides
        @ActivityScope
        MainInteractor provideMainInteractor(MainInteractorMockImpl interactor) {
            return interactor;
        }

        @Provides
        @ActivityScope
        MainPresenter provideMainPresenter(MainPresenterImpl presenter) {
            return presenter;
        }
    }

    /**
     * Extending MainFragmentComponent to support injecting MainActivityMockModule's objects in the test class ...
     */
    @ActivityScope
    @Subcomponent(
            modules = {MainActivityMockModule.class}
    )
    public interface MainActivityTestComponent extends MainFragmentComponent {
        void inject(MainPresenterTest mainPresenterTest);
    }

    /**
     * Extending AppComponent to add new Test Subcomponent (MainActivityTestComponent) add() method.
     */
    @Singleton
    @Component(modules = {AppModule.class, ServiceModule.class})
    public interface TestAppComponent extends AppComponent {
        MainActivityTestComponent add(MainActivityMockModule module);
    }

    private String result;
    private String error;
}
