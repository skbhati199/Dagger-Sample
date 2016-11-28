package com.test.xyz.daggersample.view.fragment.repolist;

import com.test.xyz.daggersample.di.scope.ActivityScope;
import com.test.xyz.daggersample.interactor.MainInteractor;
import com.test.xyz.daggersample.interactor.MainInteractorImpl;
import com.test.xyz.daggersample.presenter.repolist.RepoListPresenter;
import com.test.xyz.daggersample.presenter.repolist.RepoListPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoListFragmentModule {
    public RepoListFragmentModule(RepoListView view) {
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

    private final RepoListView view;
}
