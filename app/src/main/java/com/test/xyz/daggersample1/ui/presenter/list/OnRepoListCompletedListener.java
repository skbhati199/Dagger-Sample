package com.test.xyz.daggersample1.ui.presenter.list;

public interface OnRepoListCompletedListener {
    public void onRepoListRetrievalSuccess(String[] data);
    public void onRepoListRetrievalFailure(String errorMessage);
}
