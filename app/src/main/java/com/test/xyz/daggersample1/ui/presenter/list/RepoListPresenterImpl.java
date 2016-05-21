package com.test.xyz.daggersample1.ui.presenter.list;

import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.ui.activity.repolist.RepoListView;

import javax.inject.Inject;

public class RepoListPresenterImpl implements RepoListPresenter, OnRepoListCompletedListener {
    private RepoListView repoListView;
    private MainInteractor mainInteractor;

    @Inject
    public RepoListPresenterImpl(RepoListView repoListView, MainInteractor mainInteractor) {
        this.repoListView = repoListView;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void requestRepoList(String userName) {
        mainInteractor.getRepoList(userName, this);
    }

    @Override
    public void onRepoListRetrievalSuccess(String[] data) {
        repoListView.showRepoList(data);
    }

    @Override
    public void onRepoListRetrievalFailure(String errorMessage) {
        repoListView.showError(errorMessage);
    }
}
