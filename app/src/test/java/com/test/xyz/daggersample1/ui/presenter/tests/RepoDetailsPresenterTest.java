package com.test.xyz.daggersample1.ui.presenter.tests;

import com.test.xyz.daggersample1.di.DaggerApplication;
import com.test.xyz.daggersample1.di.ServiceModule;
import com.test.xyz.daggersample1.di.component.AppComponent;
import com.test.xyz.daggersample1.di.module.AppModule;
import com.test.xyz.daggersample1.di.scope.ActivityScope;
import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.ui.activity.repodetails.RepoDetailsActivityComponent;
import com.test.xyz.daggersample1.ui.activity.repodetails.RepoDetailsView;
import com.test.xyz.daggersample1.ui.activity.repolist.RepoListView;
import com.test.xyz.daggersample1.ui.presenter.details.RepoDetailsPresenter;
import com.test.xyz.daggersample1.ui.presenter.details.RepoDetailsPresenterImpl;
import com.test.xyz.daggersample1.ui.presenter.list.RepoListPresenter;
import com.test.xyz.daggersample1.ui.presenter.list.RepoListPresenterImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(application = DaggerApplication.class)
public class RepoDetailsPresenterTest {
    private static String USER_NAME = "hazems";
    private static String PROJECT_ID = "test";

    private String result;
    private String error;

    @Inject
    RepoDetailsPresenter repoDetailsPresenter;

    RepoDetailsView repoDetailsView = mock(RepoDetailsView.class);

    @Before
    public void setup() {

        DaggerApplication daggerApplication = (DaggerApplication) RuntimeEnvironment.application;

        TestAppComponent testAppComponent = DaggerRepoDetailsPresenterTest_TestAppComponent.builder()
                .appModule(new AppModule(daggerApplication))
                .build();

        RepoDetailsTestComponent repoDetailsTestComponent = testAppComponent.add(new RepoDetailsActivityMockModule(repoDetailsView));

        repoDetailsTestComponent.inject(this);
    }

    @After
    public void teardown() {
    }

    @Test
    public void testGetRepoDetails() throws Exception {
        repoDetailsPresenter.requestRepoDetails(USER_NAME, PROJECT_ID);

        verify(repoDetailsView, times(1)).showRepoDetails(Matchers.any(String.class));
        verify(repoDetailsView, never()).showError(Matchers.any(String.class));
    }

    @Test
    public void testGetRepoListWithoutUserName() throws Exception {
        repoDetailsPresenter.requestRepoDetails("", PROJECT_ID);

        verify(repoDetailsView, never()).showRepoDetails(Matchers.any(String.class));
        verify(repoDetailsView, times(1)).showError(Matchers.any(String.class));
    }

    @Test
    public void testGetRepoListWithoutProjectID() throws Exception {
        repoDetailsPresenter.requestRepoDetails(USER_NAME, "");

        verify(repoDetailsView, never()).showRepoDetails(Matchers.any(String.class));
        verify(repoDetailsView, times(1)).showError(Matchers.any(String.class));
    }

    /**
     * Mock Activity Module to provide mocked interactor implementation ...
     */
    @Module
    public class RepoDetailsActivityMockModule {

        public final RepoDetailsView view;

        public RepoDetailsActivityMockModule(RepoDetailsView view) {
            this.view = view;
        }

        @Provides
        @ActivityScope
        RepoDetailsView provideRepoDetailsView() {
            return this.view;
        }

        @Provides
        @ActivityScope
        MainInteractor provideMainInteractor(MainInteractorMockImpl interactor) {
            return interactor;
        }

        @Provides
        @ActivityScope
        RepoDetailsPresenter provideRepoDetailsPresenter(RepoDetailsPresenterImpl presenter) {
            return presenter;
        }
    }

    /**
     * Extending RepoDetailsActivityComponent to support injecting RepoDetailsActivityMockModule's objects in the test class ...
     */
    @ActivityScope
    @Subcomponent(
            modules = {RepoDetailsActivityMockModule.class}
    )
    public interface RepoDetailsTestComponent extends RepoDetailsActivityComponent {
        void inject(RepoDetailsPresenterTest repoDetailsPresenterTest);
    }

    /**
     * Extending AppComponent to add new Test Subcomponent (MainActivityTestComponent) add() method.
     */
    @Singleton
    @Component(modules = {AppModule.class, ServiceModule.class})
    public interface TestAppComponent extends AppComponent {
        RepoDetailsTestComponent add(RepoDetailsActivityMockModule module);
    }
}
