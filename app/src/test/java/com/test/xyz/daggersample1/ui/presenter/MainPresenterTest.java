package com.test.xyz.daggersample1.ui.presenter;

import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.ui.fragment.main.MainView;
import com.test.xyz.daggersample1.ui.presenter.main.MainPresenter;
import com.test.xyz.daggersample1.ui.presenter.main.MainPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest extends BasePresenterTest {
    private static final String USER_NAME = "hazems";
    private static final String CITY = "New York, USA";

    MainPresenter mainPresenter;

    @Mock
    MainInteractor mainInteractor;

    @Mock
    MainView mainView;

    @Before
    public void setup() {
        mockInteractor(mainInteractor);

        // Instantiate main object
        mainPresenter = new MainPresenterImpl(mainView, mainInteractor);
    }

    @Test
    public void testGetValidInformation() throws Exception {

        // Here we need to set the view to some data in order to test the main presenter ...
        when(mainView.getUserNameText()).thenReturn(USER_NAME);
        when(mainView.getCityText()).thenReturn(CITY);

        mainPresenter.requestInformation();

        verify(mainView, times(1)).hideBusyIndicator();
        verify(mainView, times(1)).showResult(MOCK_INFO_SUCCESS_MSG);
    }

    @Test
    public void testGetInformationForEmptyUser() throws Exception {

        // Here we need to set the view to some data in order to test the main presenter ...
        when(mainView.getUserNameText()).thenReturn("");
        when(mainView.getCityText()).thenReturn(CITY);

        mainPresenter.requestInformation();

        verify(mainView, times(1)).hideBusyIndicator();
        verify(mainView, times(1)).showUserNameError(R.string.username_invalid_message);
        verify(mainView, never()).showResult(any(String.class));
    }

    @Test
    public void testGetInformationForEmptyCity() throws Exception {

        // Here we need to set the view to some data in order to test the main presenter ...
        when(mainView.getUserNameText()).thenReturn(USER_NAME);
        when(mainView.getCityText()).thenReturn("");

        mainPresenter.requestInformation();

        verify(mainView, times(1)).hideBusyIndicator();
        verify(mainView, times(1)).showCityNameError(R.string.city_invalid_message);
        verify(mainView, never()).showResult(any(String.class));
    }

    private String result;
    private String error;
}
