package com.test.xyz.daggersample.service.impl;


import com.test.xyz.daggersample.service.api.GithubHttpService;
import com.test.xyz.daggersample.service.api.RepoListService;
import com.test.xyz.daggersample.service.api.model.Repo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    public Observable<List<String>> retrieveRepoList(String userName) {
        Observable<List<String>> result = service.getRepoList(userName)
                .map(new Func1<List<Repo>, List<String>>() {
                    @Override
                    public List<String> call(List<Repo> repoList) {
                        List<String> repos = new ArrayList<String>();

                        for (Repo repo : repoList) {
                            repos.add(repo.name);
                        }

                        return repos;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return result;
    }

    public Observable<String> retrieveRepoItemDetails(String userName, String projectID) {
        Observable<String> repoCall = service.getRepoItemDetails(userName, projectID)
                .map(new Func1<Repo, String>() {
                    @Override
                    public String call(Repo repo) {
                        return repo.name;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return repoCall;
    }
}
