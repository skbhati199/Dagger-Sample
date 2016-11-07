package com.test.xyz.daggersample.view.fragment.main;

import com.test.xyz.daggersample.di.scope.ActivityScope;
import com.test.xyz.daggersample.interactor.MainInteractor;
import com.test.xyz.daggersample.interactor.MainInteractorImpl;
import com.test.xyz.daggersample.presenter.main.MainPresenter;
import com.test.xyz.daggersample.presenter.main.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MainFragmentModule {
    public MainFragmentModule(MainView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    MainView provideMainView() {
        return this.view;
    }

    @Provides
    @ActivityScope
    MainInteractor provideMainInteractor(MainInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    MainPresenter provideMainPresenter(MainPresenterImpl presenter) {
        return presenter;
    }

    private final MainView view;
}
