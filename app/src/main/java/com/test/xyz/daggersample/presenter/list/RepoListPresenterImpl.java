package com.test.xyz.daggersample.presenter.list;

import com.test.xyz.daggersample.interactor.MainInteractor;
import com.test.xyz.daggersample.view.fragment.repolist.RepoListView;

import java.util.List;

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
    public void onRepoListRetrievalSuccess(List<String> data) {
        repoListView.showRepoList(data);
    }

    @Override
    public void onRepoListRetrievalFailure(String errorMessage) {
        repoListView.showError(errorMessage);
    }
}
