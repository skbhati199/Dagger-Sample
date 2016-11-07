package com.test.xyz.daggersample.presenter.main;

public interface OnInfoCompletedListener {
    public void onUserNameValidationError(int messageID);
    public void onCityValidationError(int messageID);
    public void onSuccess(String data);
    public void onFailure(String errorMessage);
}
