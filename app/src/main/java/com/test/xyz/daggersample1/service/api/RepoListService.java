package com.test.xyz.daggersample1.service.api;

import java.util.List;

import rx.Observable;

public interface RepoListService {
    public Observable<List<String>> retrieveRepoList(String userName);
    public Observable<String> retrieveRepoItemDetails(String userName, String projectID);
}
