package com.test.xyz.daggersample1.ui.presenter.tests;

import com.test.xyz.daggersample1.di.DaggerApplication;
import com.test.xyz.daggersample1.di.ServiceModule;
import com.test.xyz.daggersample1.di.component.AppComponent;
import com.test.xyz.daggersample1.di.module.AppModule;
import com.test.xyz.daggersample1.di.scope.ActivityScope;
import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.tests.common.TestConfig;
import com.test.xyz.daggersample1.ui.activity.main.MainActivityComponent;
import com.test.xyz.daggersample1.ui.activity.main.MainActivityModule;
import com.test.xyz.daggersample1.ui.activity.main.MainView;
import com.test.xyz.daggersample1.ui.presenter.main.OnInfoCompletedListener;

import org.junit.After;
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
    private static String USER_NAME = "hazems";
    private static String GREET_PREFIX = "Hello ";
    private static String CITY = "Cairo, Egypt";
    private String result;
    private String error;

    @Inject
    MainInteractor mainInteractor;

    MainView mainView = mock(MainView.class);

    @Before
    public void setup() {
        DaggerApplication daggerApplication = (DaggerApplication) RuntimeEnvironment.application;

        TestAppComponent testAppComponent = DaggerMainInteractorTest_TestAppComponent.builder()
                .appModule(new AppModule(daggerApplication))
                .build();

        MainActivityTestComponent mainActivityTestComponent = testAppComponent.add(new MainActivityModule(mainView));

        mainActivityTestComponent.inject(this);
    }

    @After
    public void teardown() {
    }

    @Test
    public void testGetInformation() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        mainInteractor.getInformation("hazem", "Cairo, Egypt", new OnInfoCompletedListener() {
            @Override
            public void onUserNameValidationError(int messageID) {
                error = "Username error!";
                countDownLatch.countDown();
            }

            @Override
            public void onCityValidationError(int messageID) {
                error = "City error!";
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
        System.out.println("Result = " + result);
    }

    //TODO Add other tests once repo list services are implemented ...

    @ActivityScope
    @Subcomponent(
            modules = {MainActivityModule.class}
    )
    public interface MainActivityTestComponent extends MainActivityComponent {
        void inject(MainInteractorTest mainPresenterTest);
    }

    @Singleton
    @Component(modules = {AppModule.class, ServiceModule.class})
    public interface TestAppComponent extends AppComponent {
        MainActivityTestComponent add(MainActivityModule module);
    }
}
