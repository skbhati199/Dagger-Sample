package com.test.xyz.daggersample.presenter.main;

import com.test.xyz.daggersample.presenter.BasePresenterTest;
import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample.interactor.MainInteractor;
import com.test.xyz.daggersample.view.fragment.main.MainView;

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
        when(mainView.getCityText()).thenReturn(CITY);

        //WHEN
        mainPresenter.requestInformation();

        //THEN
        verify(mainView, times(1)).hideBusyIndicator();
        verify(mainView, times(1)).showResult(MOCK_INFO_SUCCESS_MSG);
    }

    @Test
    public void requestInformation_whenUserNameIsEmpty_shouldReturnError() throws Exception {
        //GIVEN
        when(mainView.getUserNameText()).thenReturn("");
        when(mainView.getCityText()).thenReturn(CITY);

        //WHEN
        mainPresenter.requestInformation();

        //THEN
        verify(mainView, times(1)).hideBusyIndicator();
        verify(mainView, times(1)).showUserNameError(R.string.username_invalid_message);
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
        verify(mainView, times(1)).hideBusyIndicator();
        verify(mainView, times(1)).showCityNameError(R.string.city_invalid_message);
        verify(mainView, never()).showResult(any(String.class));
    }
}
