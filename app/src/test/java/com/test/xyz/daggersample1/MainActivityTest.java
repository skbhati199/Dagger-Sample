package com.test.xyz.daggersample1;

import android.widget.EditText;
import android.widget.TextView;

import com.test.xyz.daggersample1.service.api.HelloService;
import com.test.xyz.daggersample1.service.di.DaggerApplication;
import com.test.xyz.daggersample1.service.di.DaggerGraph;
import com.test.xyz.daggersample1.service.di.MainModule;
import com.test.xyz.daggersample1.ui.MainActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class MainActivityTest {
    private static String GREET_PREFIX = "[Test] Hello ";

    @Singleton
    @Component(modules = {TestServiceModule.class, MainModule.class})
    interface TestDaggerGraph extends DaggerGraph {
    }

    @Module
    static class TestServiceModule {

        @Provides
        HelloService provideHelloService() {
            return new HelloService() {
                @Override
                public String greet(String userName) {
                    return GREET_PREFIX + userName;
                }
            };
        }
    }

    @Before
    public void setTestComponent() {
        DaggerApplication app = (DaggerApplication) RuntimeEnvironment.application;

        DaggerGraph testComponent = DaggerMainActivityTest_TestDaggerGraph.builder()
                                                                          .mainModule(new MainModule(app))
                                                                          .build();

        app.setTestComponent(testComponent);
    }

    @Test
    public void greetButtonClicked() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);

        ((EditText) mainActivity.findViewById(R.id.userNameText)).setText("Hazem");
        mainActivity.findViewById(R.id.greet).performClick();

        Assert.assertEquals(GREET_PREFIX + "Hazem", ((TextView) mainActivity.findViewById(R.id.resultView)).getText());
    }
}