package com.test.xyz.daggersample1.ui.presenter.tests;

import android.text.TextUtils;

import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.ui.presenter.details.OnRepoDetailsCompletedListener;
import com.test.xyz.daggersample1.ui.presenter.list.OnRepoListCompletedListener;
import com.test.xyz.daggersample1.ui.presenter.main.OnInfoCompletedListener;

import javax.inject.Inject;

/**
 * Mock class for the main interactor to facilitate tests ...
 */
class MainInteractorMockImpl implements MainInteractor {
    public static final String USERNAME_MUST_BE_PROVIDED = "Username must be provided!";
    public static final String SOME_REPO_DETAILS = "Some Repo Details ...";
    public static final String PROJECT_ID_HAS_TO_BE_PROVIDED = "Project ID has to be provided";

    @Inject
    public MainInteractorMockImpl() {
    }

    @Override
    public void getInformation(String userName, String cityName, OnInfoCompletedListener listener) {
        System.out.println("Mock getInformation() is called ...");

        if (TextUtils.isEmpty(userName)) {
            listener.onUserNameValidationError(R.string.username_invalid_message);
            return;
        }

        if (TextUtils.isEmpty(cityName)) {
            listener.onUserNameValidationError(R.string.city_invalid_message);
            return;
        }

        listener.onSuccess(MainPresenterTest.MOCK_INFO_SUCCESS_MSG);
    }

    @Override
    public void getRepoList(String userName, OnRepoListCompletedListener listener) {
        System.out.println("Mock getRepoList() is called ...");

        if (TextUtils.isEmpty(userName)) {
            listener.onRepoListRetrievalFailure(USERNAME_MUST_BE_PROVIDED);
            return;
        }

        String[] values = new String[] {
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        listener.onRepoListRetrievalSuccess(values);
    }

    @Override
    public void getRepoItemDetails(String userName, String projectID, OnRepoDetailsCompletedListener listener) {
        System.out.println("Mock getRepoItemDetails() is called ...");

        if (TextUtils.isEmpty(userName)) {
            listener.onRepoDetailsRetrievalFailure(USERNAME_MUST_BE_PROVIDED);
            return;
        }

        if (TextUtils.isEmpty(projectID)) {
            listener.onRepoDetailsRetrievalFailure(PROJECT_ID_HAS_TO_BE_PROVIDED);
            return;
        }

        listener.onRepoDetailsRetrievalSuccess(SOME_REPO_DETAILS);
    }
}