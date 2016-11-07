package com.test.xyz.daggersample.presenter.details;

public interface OnRepoDetailsCompletedListener {
    public void onRepoDetailsRetrievalSuccess(String data);
    public void onRepoDetailsRetrievalFailure(String errorMessage);
}
