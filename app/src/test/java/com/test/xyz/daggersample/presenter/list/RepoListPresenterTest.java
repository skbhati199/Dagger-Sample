package com.test.xyz.daggersample.presenter.list;

import com.test.xyz.daggersample.interactor.MainInteractor;
import com.test.xyz.daggersample.view.fragment.repolist.RepoListView;
import com.test.xyz.daggersample.presenter.BasePresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RepoListPresenterTest extends BasePresenterTest {
    private static final String USER_NAME = "hazems";
    private static final String CITY = "New York, USA";

    private RepoListPresenter repoListPresenter;

    @Mock
    RepoListView repoListView;

    @Mock
    MainInteractor mainInteractor;

    @Before
    public void setup() {
        mockInteractor(mainInteractor);
        repoListPresenter = new RepoListPresenterImpl(repoListView, mainInteractor);
    }

    @Test
    public void requestRepoList_whenUserNameIsValid_shouldReturnRepoList() {
        //GIVEN
        //NOTHING

        //WHEN
        repoListPresenter.requestRepoList(USER_NAME);

        //THEN
        verify(repoListView).showRepoList(Matchers.any(List.class));
        verify(repoListView, never()).showError(Matchers.any(String.class));
    }

    @Test
    public void requestRepoList_whenUserNameIsEmpty_shouldError() {
        //GIVEN
        //NOTHING

        //WHEN
        repoListPresenter.requestRepoList("");

        //THEN
        verify(repoListView, never()).showRepoList(Matchers.any(List.class));
        verify(repoListView, times(1)).showError(Matchers.any(String.class));
    }

    private String result;
    private String error;
}
