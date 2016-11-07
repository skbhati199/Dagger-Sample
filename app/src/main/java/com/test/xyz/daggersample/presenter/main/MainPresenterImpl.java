package com.test.xyz.daggersample.presenter.main;

import com.test.xyz.daggersample.interactor.MainInteractor;
import com.test.xyz.daggersample.view.fragment.main.MainView;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter {
    private MainView mainView;
    private MainInteractor mainInteractor;

    @Inject
    public MainPresenterImpl(MainView mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void requestInformation() {
        mainView.showBusyIndicator();
        mainInteractor.getInformation(mainView.getUserNameText(), mainView.getCityText(), this);
    }

    @Override
    public void onUserNameValidationError(int messageID) {
        mainView.hideBusyIndicator();
        mainView.showUserNameError(messageID);
    }

    @Override
    public void onCityValidationError(int messageID) {
        mainView.hideBusyIndicator();
        mainView.showCityNameError(messageID);
    }

    @Override
    public void onSuccess(String data) {
        mainView.hideBusyIndicator();
        mainView.showResult(data);
    }

    @Override
    public void onFailure(String errorMessage) {
        mainView.hideBusyIndicator();
        mainView.showResult(errorMessage);
    }
}
