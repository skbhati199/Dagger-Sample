package com.test.xyz.daggersample1.ui.presenter.tests;

import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.ui.fragment.repolist.RepoListView;
import com.test.xyz.daggersample1.ui.presenter.list.RepoListPresenter;
import com.test.xyz.daggersample1.ui.presenter.list.RepoListPresenterImpl;

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

    RepoListPresenter repoListPresenter;

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
    public void testGetRepoList() throws Exception {
        repoListPresenter.requestRepoList(USER_NAME);

        verify(repoListView, times(1)).showRepoList(Matchers.any(List.class));
        verify(repoListView, never()).showError(Matchers.any(String.class));
    }

    @Test
    public void testGetRepoListWithoutUserName() throws Exception {
        repoListPresenter.requestRepoList("");

        verify(repoListView, never()).showRepoList(Matchers.any(List.class));
        verify(repoListView, times(1)).showError(Matchers.any(String.class));
    }

    private String result;
    private String error;
}
