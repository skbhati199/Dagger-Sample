package com.test.xyz.daggersample1.ui.presenter.details;

public interface RepoDetailsPresenter extends OnRepoDetailsCompletedListener {
    public void requestRepoDetails(String userName, String projectID);
}
