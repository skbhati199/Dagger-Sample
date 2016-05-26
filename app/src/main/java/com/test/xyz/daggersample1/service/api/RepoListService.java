package com.test.xyz.daggersample1.service.api;

public interface RepoListService {
    public String[] retrieveRepoList(String userName);
    public String retrieveRepoItemDetails(String userName, String projectID);
}
