package com.test.xyz.daggersample.presenter;

import com.test.xyz.daggersample.interactor.MainInteractor;
import com.test.xyz.daggersample.presenter.details.OnRepoDetailsCompletedListener;
import com.test.xyz.daggersample.presenter.list.OnRepoListCompletedListener;
import com.test.xyz.daggersample.presenter.main.OnInfoCompletedListener;
import com.test.xyz.daggersample1.R;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;

public class BasePresenterTest {
    protected static final String MOCK_INFO_SUCCESS_MSG = "MOCK INFO SUCCESS MSG";
    protected static final String INVALID_CITY = "INVALID";
    protected static final String VALID_CITY = "New York, USA";
    protected static final String EMPTY_VALUE = "";

    protected void mockInteractor(MainInteractor mainInteractor) {
        mockGetInformationAPI(mainInteractor);
        mockGetRepoListAPI(mainInteractor);
        mockGetRepoItemsAPI(mainInteractor);
    }

    private void mockGetRepoItemsAPI(MainInteractor mainInteractor) {
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoDetailsCompletedListener) args[2]).onRepoDetailsRetrievalFailure(any(String.class));
                return null;
            }
        }).when(mainInteractor).getRepoItemDetails(eq(EMPTY_VALUE), any(String.class), any(OnRepoDetailsCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoDetailsCompletedListener) args[2]).onRepoDetailsRetrievalSuccess(any(String.class));
                return null;
            }
        }).when(mainInteractor).getRepoItemDetails(not(eq(EMPTY_VALUE)), any(String.class), any(OnRepoDetailsCompletedListener.class));
    }

    private void mockGetRepoListAPI(MainInteractor mainInteractor) {
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoListCompletedListener) args[1]).onRepoListRetrievalFailure("Error");
                return null;
            }
        }).when(mainInteractor).getRepoList(eq(EMPTY_VALUE), any(OnRepoListCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoListCompletedListener) args[1]).onRepoListRetrievalSuccess(any(List.class));
                return null;
            }
        }).when(mainInteractor).getRepoList(not(eq(EMPTY_VALUE)), any(OnRepoListCompletedListener.class));
    }

    private void mockGetInformationAPI(MainInteractor mainInteractor) {
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnInfoCompletedListener) args[2]).onFailure("Invalid city provided!!!");
                return null;
            }
        }).when(mainInteractor).getInformation(anyString(), eq(INVALID_CITY), any(OnInfoCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnInfoCompletedListener) args[2]).onUserNameValidationError(R.string.username_empty_message);
                return null;
            }
        }).when(mainInteractor).getInformation(eq(EMPTY_VALUE), eq(VALID_CITY), any(OnInfoCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnInfoCompletedListener) args[2]).onCityValidationError(R.string.city_empty_message);
                return null;
            }
        }).when(mainInteractor).getInformation(anyString(), eq(EMPTY_VALUE), any(OnInfoCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnInfoCompletedListener) args[2]).onSuccess(MOCK_INFO_SUCCESS_MSG);
                return null;
            }
        }).when(mainInteractor).getInformation(not(eq(EMPTY_VALUE)),
                and(not(eq(INVALID_CITY)), not(eq(EMPTY_VALUE))),
                any(OnInfoCompletedListener.class));
    }
}
