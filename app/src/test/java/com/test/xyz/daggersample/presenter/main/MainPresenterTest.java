package com.test.xyz.daggersample.presenter.main;

import com.test.xyz.daggersample.interactor.MainInteractor;
import com.test.xyz.daggersample.presenter.BasePresenterTest;
import com.test.xyz.daggersample.view.fragment.main.MainView;
import com.test.xyz.daggersample1.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest extends BasePresenterTest {
    private static final String USER_NAME = "hazems";

    private MainPresenter mainPresenter;

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
    public void requestInformation_whenUserNameAndCityAreValid_shouldReturnInfo() throws Exception {
        //GIVEN
        when(mainView.getUserNameText()).thenReturn(USER_NAME);
        when(mainView.getCityText()).thenReturn(VALID_CITY);

        //WHEN
        mainPresenter.requestInformation();

        //THEN
        //TODO comment the next line to show how it work.
        verify(mainView).showBusyIndicator();
        verify(mainView).hideBusyIndicator();
        verify(mainView).showResult(MOCK_INFO_SUCCESS_MSG);
    }

    @Test
    public void requestInformation_whenUserNameIsEmpty_shouldReturnError() throws Exception {
        //GIVEN
        when(mainView.getUserNameText()).thenReturn("");
        when(mainView.getCityText()).thenReturn(VALID_CITY);

        //WHEN
        mainPresenter.requestInformation();

        //THEN
        verify(mainView).hideBusyIndicator();
        verify(mainView).showUserNameError(R.string.username_empty_message);
        verify(mainView, never()).showResult(any(String.class));
    }

    @Test
    public void requestInformation_whenCityIsEmpty_shouldReturnError() throws Exception {
        //GIVEN
        when(mainView.getUserNameText()).thenReturn(USER_NAME);
        when(mainView.getCityText()).thenReturn("");

        //WHEN
        mainPresenter.requestInformation();

        //THEN
        verify(mainView).hideBusyIndicator();
        verify(mainView).showCityNameError(R.string.city_empty_message);
        verify(mainView, never()).showResult(any(String.class));
    }

    //TODO comment the next method to show how it work.
    /*
    @Test
    public void requestInformation_whenCityIsInvalid_shouldReturnError() throws Exception {
        //GIVEN
        when(mainView.getUserNameText()).thenReturn(USER_NAME);
        when(mainView.getCityText()).thenReturn(INVALID_CITY);

        //WHEN
        mainPresenter.requestInformation();

        //THEN
        verify(mainView).showBusyIndicator();
        verify(mainView).hideBusyIndicator();
        verify(mainView).showError(anyString());
    }
    */
}
