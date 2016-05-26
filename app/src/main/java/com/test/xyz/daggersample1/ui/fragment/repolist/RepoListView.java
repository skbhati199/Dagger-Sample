package com.test.xyz.daggersample1.ui.fragment.repolist;

public interface RepoListView {
    public void showRepoList(String[] values);
    public void showError(final String errorMessage);
}
