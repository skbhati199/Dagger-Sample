package com.test.xyz.daggersample;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;

import com.test.xyz.daggersample.di.DaggerApplication;
import com.test.xyz.daggersample.di.component.AppComponent;
import com.test.xyz.daggersample.di.module.AppModule;
import com.test.xyz.daggersample.service.api.HelloService;
import com.test.xyz.daggersample.service.api.RepoListService;
import com.test.xyz.daggersample.service.api.WeatherService;
import com.test.xyz.daggersample.service.exception.InvalidCityException;
import com.test.xyz.daggersample.view.activity.main.MainActivity;
import com.test.xyz.daggersample.util.DaggerActivityTestRule;
import com.test.xyz.daggersample.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import rx.Observable;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private static String GREET_PREFIX = "[Test] Hello ";
    private static int MOCK_TEMPERATURE = 65;
    private static String MOCK_NAME = "Hazem";
    private static String MOCK_PLACE = "NYC, USA";
    private static String MOCK_GREETING_MSG = GREET_PREFIX + MOCK_NAME;
    private static String MOCK_WEATHER_MSG = "\nCurrent weather in " + MOCK_PLACE + " is " + MOCK_TEMPERATURE + "°F";

    private static String MOCK_INFO_RESPONSE_MESSAGE = MOCK_GREETING_MSG + MOCK_WEATHER_MSG;
    private static String MOCK_REPO_DETAILS_RESPONSE_MESSAGE = "Repo Details ...";


    private static String TAG = MainActivityTest.class.getName();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new DaggerActivityTestRule<>(MainActivity.class, new DaggerActivityTestRule.OnBeforeActivityLaunchedListener<MainActivity>() {
                @Override
                public void beforeActivityLaunched(@NonNull Application application, @NonNull MainActivity activity) {
                    DaggerApplication app = (DaggerApplication) application;

                    AppComponent mTestAppComponent = DaggerMainActivityTest_TestAppComponent.builder()
                            .appModule(new AppModule(app))
                            .build();

                    app.setTestComponent(mTestAppComponent);
                }
            });

    @Singleton
    @Component(modules = {TestServiceModule.class, AppModule.class})
    interface TestAppComponent extends AppComponent {
    }

    @Module
    static class TestServiceModule {

        @Provides
        @Singleton
        HelloService provideHelloService() {
            return new HelloService() {
                @Override
                public String greet(String userName) {
                    return GREET_PREFIX + userName;
                }
            };
        }

        @Provides
        @Singleton
        WeatherService provideWeatherService() {
            return new WeatherService() {
                @Override
                public int getWeatherInfo(String city) throws InvalidCityException {
                    return 65;
                }
            };
        }

        @Provides
        @Singleton
        RepoListService provideRepoListService() {
            return new RepoListService() {

                @Override
                public Observable<List<String>> retrieveRepoList(String userName) {
                    List<String> repoList = Arrays.asList(new String[] {
                                    "Repo1",
                                    "Repo2"
                            });

                    return Observable.just(repoList);
                }

                @Override
                public Observable<String> retrieveRepoItemDetails(String userName, String projectID) {
                    return Observable.just(MOCK_REPO_DETAILS_RESPONSE_MESSAGE);
                }
            };
        }
    }

    @Test
    public void showInformationAction() {
        onView(withId(R.id.userNameText))
                .perform(typeText(MOCK_NAME), closeSoftKeyboard());

        onView(withId(R.id.cityText)).perform(clearText(), typeText(MOCK_PLACE), closeSoftKeyboard());

        onView(withId(R.id.btnShowInfo)).perform(click());

        onView(withId(R.id.resultView)).check(matches(withText(MOCK_INFO_RESPONSE_MESSAGE)));
    }

    @Test
    public void showRepoListAction() {
        // Open settings button ...
        //openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        //onView(withText(R.string.nav_item_repo_list)).perform(click());

        // Open the navigation drawer ...
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());

        // Just give 1 second for the drawer layout to open ...
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the navigation item repo list ...
        onView(withText(R.string.nav_item_repo_list)).perform(click());

        // Check if the list has two items ...
        final int[] counts = new int[1];

        onView(withId(R.id.repoList)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                counts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        onData(anything()).inAdapterView(withId(R.id.repoList)).atPosition(1).check(matches(isDisplayed()));
    }

    @Test
    public void showRepoDetailsAction() {

        // Open the navigation drawer ...
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());

        // Just give 1 second for the drawer layout to open ...
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the navigation item repo list ...
        onView(withText(R.string.nav_item_repo_list)).perform(click());

        // Check if the list has two items ...
        final int[] counts = new int[1];

        onView(withId(R.id.repoList)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                counts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        onData(anything()).inAdapterView(withId(R.id.repoList)).atPosition(1).perform(click());

        onView(withId(R.id.repoDetails)).check(matches(withText(MOCK_REPO_DETAILS_RESPONSE_MESSAGE)));
    }

    // This is a workaround to open navigation drawer since it is not supported OOB yet by Espresso!
    private static ViewAction actionOpenDrawer() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(DrawerLayout.class);
            }

            @Override
            public String getDescription() {
                return "open drawer";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((DrawerLayout) view).openDrawer(GravityCompat.START);
            }
        };
    }

    private static ViewAction actionCloseDrawer() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(DrawerLayout.class);
            }

            @Override
            public String getDescription() {
                return "close drawer";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((DrawerLayout) view).closeDrawer(GravityCompat.START);
            }
        };
    }
}
