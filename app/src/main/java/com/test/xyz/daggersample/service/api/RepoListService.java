package com.test.xyz.daggersample.service.api;

import java.util.List;

import rx.Observable;

public interface RepoListService {
    Observable<List<String>> retrieveRepoList(String userName);
    Observable<String> retrieveRepoItemDetails(String userName, String projectID);
}
