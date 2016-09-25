package com.test.xyz.daggersample1.ui.presenter;

import com.test.xyz.daggersample1.R;
import com.test.xyz.daggersample1.interactor.MainInteractor;
import com.test.xyz.daggersample1.ui.presenter.details.OnRepoDetailsCompletedListener;
import com.test.xyz.daggersample1.ui.presenter.list.OnRepoListCompletedListener;
import com.test.xyz.daggersample1.ui.presenter.main.OnInfoCompletedListener;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;

public class BasePresenterTest {
    static final String MOCK_INFO_SUCCESS_MSG = "MOCK INFO SUCCESS MSG";

    protected void mockInteractor(MainInteractor mainInteractor) {

        // Mock getInformation
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnInfoCompletedListener) args[2]).onUserNameValidationError(R.string.username_invalid_message);
                return null;
            }
        }).when(mainInteractor).getInformation(eq(""), any(String.class), any(OnInfoCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnInfoCompletedListener) args[2]).onCityValidationError(R.string.city_invalid_message);
                return null;
            }
        }).when(mainInteractor).getInformation(any(String.class), eq(""), any(OnInfoCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnInfoCompletedListener) args[2]).onSuccess(MOCK_INFO_SUCCESS_MSG);
                return null;
            }
        }).when(mainInteractor).getInformation(not(eq("")), not(eq("")), any(OnInfoCompletedListener.class));

        // Mock getRepoList
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoListCompletedListener) args[1]).onRepoListRetrievalFailure("Error");
                return null;
            }
        }).when(mainInteractor).getRepoList(eq(""), any(OnRepoListCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoListCompletedListener) args[1]).onRepoListRetrievalSuccess(any(List.class));
                return null;
            }
        }).when(mainInteractor).getRepoList(not(eq("")), any(OnRepoListCompletedListener.class));

        // Mock getRepoItemDetails
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoDetailsCompletedListener) args[2]).onRepoDetailsRetrievalFailure(any(String.class));
                return null;
            }
        }).when(mainInteractor).getRepoItemDetails(eq(""), any(String.class), any(OnRepoDetailsCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoDetailsCompletedListener) args[2]).onRepoDetailsRetrievalSuccess(any(String.class));
                return null;
            }
        }).when(mainInteractor).getRepoItemDetails(not(eq("")), any(String.class), any(OnRepoDetailsCompletedListener.class));
    }
}
