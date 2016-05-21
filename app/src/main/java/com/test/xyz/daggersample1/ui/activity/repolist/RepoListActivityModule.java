package com.test.xyz.daggersample1.ui.activity.repolist;

import com.test.xyz.daggersample1.di.scope.ActivityScope;
import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.interactor.MainInteractorImpl;
import com.test.xyz.daggersample1.ui.presenter.list.RepoListPresenter;
import com.test.xyz.daggersample1.ui.presenter.list.RepoListPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoListActivityModule {

    public final RepoListView view;

    public RepoListActivityModule(RepoListView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    RepoListView provideRepoListView() {
        return this.view;
    }

    @Provides
    @ActivityScope
    MainInteractor provideMainInteractor(MainInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    RepoListPresenter provideRepoListPresenter(RepoListPresenterImpl presenter) {
        return presenter;
    }
}
