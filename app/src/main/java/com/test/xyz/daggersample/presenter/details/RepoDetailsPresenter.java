package com.test.xyz.daggersample.presenter.details;

public interface RepoDetailsPresenter extends OnRepoDetailsCompletedListener {
    public void requestRepoDetails(String userName, String projectID);
}
