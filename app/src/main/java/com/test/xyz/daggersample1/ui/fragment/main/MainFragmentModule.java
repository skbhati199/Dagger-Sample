package com.test.xyz.daggersample1.ui.fragment.main;

import com.test.xyz.daggersample1.di.scope.ActivityScope;
import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.interactor.MainInteractorImpl;
import com.test.xyz.daggersample1.ui.presenter.main.MainPresenter;
import com.test.xyz.daggersample1.ui.presenter.main.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MainFragmentModule {

    public final MainView view;

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
}
