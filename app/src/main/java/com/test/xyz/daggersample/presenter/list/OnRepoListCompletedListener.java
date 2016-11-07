package com.test.xyz.daggersample.presenter.list;

import java.util.List;

public interface OnRepoListCompletedListener {
    public void onRepoListRetrievalSuccess(List<String> data);
    public void onRepoListRetrievalFailure(String errorMessage);
}
