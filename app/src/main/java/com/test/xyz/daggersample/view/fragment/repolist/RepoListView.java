package com.test.xyz.daggersample.view.fragment.repolist;

import java.util.List;

public interface RepoListView {
    void showRepoList(List<String> values);
    void showError(final String errorMessage);
}
