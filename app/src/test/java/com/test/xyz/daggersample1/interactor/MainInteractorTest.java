package com.test.xyz.daggersample1.interactor;

import com.test.xyz.daggersample1.di.DaggerApplication;
import com.test.xyz.daggersample1.di.ServiceModule;
import com.test.xyz.daggersample1.di.component.AppComponent;
import com.test.xyz.daggersample1.di.module.AppModule;
import com.test.xyz.daggersample1.di.scope.ActivityScope;
import com.test.xyz.daggersample1.common.TestConfig;
import com.test.xyz.daggersample1.ui.fragment.main.MainFragmentComponent;
import com.test.xyz.daggersample1.ui.fragment.main.MainFragmentModule;
import com.test.xyz.daggersample1.ui.fragment.main.MainView;
import com.test.xyz.daggersample1.ui.presenter.main.OnInfoCompletedListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(application = DaggerApplication.class)
public class MainInteractorTest {
    private static final String USER_NAME = "hazems";
    private static final String CITY = "New York, USA";
    private static final String USERNAME_ERROR = "Username error!";
    private static final String CITY_ERROR = "City error!";
    private String result;
    private String error;

    @Inject
    MainInteractor mainInteractor;

    MainView mainView;

    @Before
    public void setup() {
        DaggerApplication daggerApplication = (DaggerApplication) RuntimeEnvironment.application;

        TestAppComponent testAppComponent = DaggerMainInteractorTest_TestAppComponent.builder()
                .appModule(new AppModule(daggerApplication))
                .build();

        mainView = mock(MainView.class);

        MainActivityTestComponent mainActivityTestComponent = testAppComponent.add(new MainFragmentModule(mainView));

        mainActivityTestComponent.inject(this);
    }

    @Test
    public void testGetInformation() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        mainInteractor.getInformation(USER_NAME, CITY, new OnInfoCompletedListener() {
            @Override
            public void onUserNameValidationError(int messageID) {
                error = USERNAME_ERROR;
                countDownLatch.countDown();
            }

            @Override
            public void onCityValidationError(int messageID) {
                error = CITY_ERROR;
                countDownLatch.countDown();
            }

            @Override
            public void onSuccess(String data) {
                result = data;
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(String errorMessage) {
                error = errorMessage;
                countDownLatch.countDown();
            }
        });

        countDownLatch.await(TestConfig.TIMEOUT, TimeUnit.MILLISECONDS);

        Assert.assertNull("Error", error);
        Assert.assertNotNull("Result cannot be null!!!", result);
    }

    @ActivityScope
    @Subcomponent(
            modules = {MainFragmentModule.class}
    )
    public interface MainActivityTestComponent extends MainFragmentComponent {
        void inject(MainInteractorTest mainPresenterTest);
    }

    @Singleton
    @Component(modules = {AppModule.class, ServiceModule.class})
    public interface TestAppComponent extends AppComponent {
        MainActivityTestComponent add(MainFragmentModule module);
    }
}
