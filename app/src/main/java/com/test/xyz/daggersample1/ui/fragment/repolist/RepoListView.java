package com.test.xyz.daggersample1.ui.fragment.repolist;

import java.util.List;

public interface RepoListView {
    public void showRepoList(List<String> values);
    public void showError(final String errorMessage);
}
