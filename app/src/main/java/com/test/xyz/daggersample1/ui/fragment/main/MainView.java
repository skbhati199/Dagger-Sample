package com.test.xyz.daggersample1.ui.fragment.main;

public interface MainView {
    public String getUserNameText();
    public String getCityText();

    public void showUserNameError(int messageId);
    public void showCityNameError(int messageId);

    public void showBusyIndicator();
    public void hideBusyIndicator();

    public void showResult(String result);
    public void showError(String error);
}
