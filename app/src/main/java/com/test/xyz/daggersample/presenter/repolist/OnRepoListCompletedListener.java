package com.test.xyz.daggersample.presenter.repolist;

import com.test.xyz.daggersample.service.api.model.Repo;

import java.util.List;

public interface OnRepoListCompletedListener {
    void onRepoListRetrievalSuccess(List<Repo> data);

    void onRepoListRetrievalFailure(String errorMessage);
}
