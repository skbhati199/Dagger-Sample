package com.test.xyz.daggersample1.interactor;

import com.test.xyz.daggersample1.ui.presenter.details.OnRepoDetailsCompletedListener;
import com.test.xyz.daggersample1.ui.presenter.list.OnRepoListCompletedListener;
import com.test.xyz.daggersample1.ui.presenter.main.OnInfoCompletedListener;

public interface MainInteractor {
    public void getInformation(String userName, String cityName, final OnInfoCompletedListener listener);
    public void getRepoList(String userName, final OnRepoListCompletedListener listener);
    public void getRepoItemDetails(String userName, String projectID, final OnRepoDetailsCompletedListener listener);
}
