package com.test.xyz.daggersample.presenter.repodetails;

public interface RepoDetailsPresenter extends OnRepoDetailsCompletedListener {
    void requestRepoDetails(String userName, String projectID);
}
