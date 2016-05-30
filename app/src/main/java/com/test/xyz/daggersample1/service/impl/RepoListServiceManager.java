package com.test.xyz.daggersample1.service.impl;


import android.util.Log;

import com.test.xyz.daggersample1.service.api.GithubHttpService;
import com.test.xyz.daggersample1.service.api.RepoListService;
import com.test.xyz.daggersample1.service.api.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoListServiceManager implements RepoListService {
    private static final String TAG = RepoListServiceManager.class.getName();
    private static final String HTTPS_API_GITHUB_URL = "https://api.github.com/";

    private GithubHttpService service;

    public RepoListServiceManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HTTPS_API_GITHUB_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubHttpService.class);
    }

    public String[] retrieveRepoList(String userName) {
        String[] repos = null;

        Call<List<Repo>> repoListCall = service.getRepoList(userName);

        try {
            Response<List<Repo>> repoListResponse = repoListCall.execute();

            List<Repo> repoList = repoListResponse.body();
            repos = new String[repoList.size()];
            int i = 0;

            for (Repo repo : repoList) {
                repos[i++] = repo.name;
            }

            Log.d(TAG, repos.length + "");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return repos;
    }

    public String retrieveRepoItemDetails(String userName, String projectID) {
        Call<Repo> repoCall = service.getRepoItemDetails(userName, projectID);

        try {
            Response<Repo> repoResponse = repoCall.execute();

            Repo repo = repoResponse.body();

            return repo.description;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return "";
    }
}
