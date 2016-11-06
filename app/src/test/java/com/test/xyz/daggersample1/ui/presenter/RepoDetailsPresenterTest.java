package com.test.xyz.daggersample1.ui.presenter;

import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.ui.activity.repodetails.RepoDetailsView;
import com.test.xyz.daggersample1.ui.presenter.details.RepoDetailsPresenter;
import com.test.xyz.daggersample1.ui.presenter.details.RepoDetailsPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RepoDetailsPresenterTest  extends BasePresenterTest {
    private static final String USER_NAME = "hazems";
    private static final String PROJECT_ID = "test";

    private RepoDetailsPresenter repoDetailsPresenter;

    @Mock
    MainInteractor mainInteractor;

    @Mock
    RepoDetailsView repoDetailsView;

    @Before
    public void setup() {
        mockInteractor(mainInteractor);
        repoDetailsPresenter = new RepoDetailsPresenterImpl(repoDetailsView, mainInteractor);
    }

    @Test
    public void testGetRepoDetails() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter.requestRepoDetails(USER_NAME, PROJECT_ID);

        //THEN
        verify(repoDetailsView).showRepoDetails(Matchers.any(String.class));
        verify(repoDetailsView, never()).showError(Matchers.any(String.class));
    }

    @Test
    public void testGetRepoListWithoutUserName() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter.requestRepoDetails("", PROJECT_ID);

        //THEN
        verify(repoDetailsView, never()).showRepoDetails(Matchers.any(String.class));
        verify(repoDetailsView).showError(Matchers.any(String.class));
    }
}
