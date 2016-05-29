package com.test.xyz.daggersample1.ui.presenter.tests;

import com.test.xyz.daggersample1.di.DaggerApplication;
import com.test.xyz.daggersample1.di.ServiceModule;
import com.test.xyz.daggersample1.di.component.AppComponent;
import com.test.xyz.daggersample1.di.module.AppModule;
import com.test.xyz.daggersample1.di.scope.ActivityScope;
import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.ui.fragment.repolist.RepoListFragmentComponent;
import com.test.xyz.daggersample1.ui.fragment.repolist.RepoListView;
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
public class RepoListPresenterTest {
    private static String USER_NAME = "hazems";
    private static String CITY = "Cairo, Egypt";
    public static String MOCK_INFO_SUCCESS_MSG = "MOCK INFO SUCCESS MSG";

    private String result;
    private String error;

    @Inject
    RepoListPresenter repoListPresenter;

    RepoListView repoListView = mock(RepoListView.class);

    @Before
    public void setup() {

        DaggerApplication daggerApplication = (DaggerApplication) RuntimeEnvironment.application;

        TestAppComponent testAppComponent = DaggerRepoListPresenterTest_TestAppComponent.builder()
                .appModule(new AppModule(daggerApplication))
                .build();

        RepoListTestComponent repoListTestComponent = testAppComponent.add(new RepoListActivityMockModule(repoListView));

        repoListTestComponent.inject(this);
    }

    @After
    public void teardown() {
    }

    @Test
    public void testGetRepoList() throws Exception {
        repoListPresenter.requestRepoList(USER_NAME);

        verify(repoListView, times(1)).showRepoList(Matchers.any(String[].class));
        verify(repoListView, never()).showError(Matchers.any(String.class));
    }

    @Test
    public void testGetRepoListWithoutUserName() throws Exception {
        repoListPresenter.requestRepoList("");

        verify(repoListView, never()).showRepoList(Matchers.any(String[].class));
        verify(repoListView, times(1)).showError(Matchers.any(String.class));
    }

    /**
     * Mock Activity Module to provide mocked interactor implementation ...
     */
    @Module
    public class RepoListActivityMockModule {

        public final RepoListView view;

        public RepoListActivityMockModule(RepoListView view) {
            this.view = view;
        }

        @Provides
        @ActivityScope
        RepoListView provideRepoListView() {
            return this.view;
        }

        @Provides
        @ActivityScope
        MainInteractor provideMainInteractor(MainInteractorMockImpl interactor) {
            return interactor;
        }

        @Provides
        @ActivityScope
        RepoListPresenter provideRepoListPresenter(RepoListPresenterImpl presenter) {
            return presenter;
        }
    }

    /**
     * Extending RepoListFragmentComponent to support injecting RepoListActivityMockModule's objects in the test class ...
     */
    @ActivityScope
    @Subcomponent(
            modules = {RepoListActivityMockModule.class}
    )
    public interface RepoListTestComponent extends RepoListFragmentComponent {
        void inject(RepoListPresenterTest repoListPresenterTest);
    }

    /**
     * Extending AppComponent to add new Test Subcomponent (MainActivityTestComponent) add() method.
     */
    @Singleton
    @Component(modules = {AppModule.class, ServiceModule.class})
    public interface TestAppComponent extends AppComponent {
        RepoListTestComponent add(RepoListActivityMockModule module);
    }
}
