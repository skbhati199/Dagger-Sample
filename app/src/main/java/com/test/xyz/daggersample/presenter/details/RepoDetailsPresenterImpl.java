package com.test.xyz.daggersample.presenter.details;

import com.test.xyz.daggersample.interactor.MainInteractor;
import com.test.xyz.daggersample.service.api.model.Repo;
import com.test.xyz.daggersample.view.activity.repodetails.RepoDetailsView;

import javax.inject.Inject;

public class RepoDetailsPresenterImpl implements RepoDetailsPresenter {
    private RepoDetailsView repoDetailsView;
    private MainInteractor mainInteractor;

    @Inject
    public RepoDetailsPresenterImpl(RepoDetailsView repoDetailsView, MainInteractor mainInteractor) {
        this.repoDetailsView = repoDetailsView;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void requestRepoDetails(String userName, String projectID) {
        mainInteractor.getRepoItemDetails(userName, projectID, this);
    }

    @Override
    public void onRepoDetailsRetrievalSuccess(Repo data) {
        repoDetailsView.showRepoDetails(data);
    }

    @Override
    public void onRepoDetailsRetrievalFailure(String errorMessage) {
        repoDetailsView.showError(errorMessage);
    }
}
