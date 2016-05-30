package com.test.xyz.daggersample1.service.api;

import com.test.xyz.daggersample1.service.api.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubHttpService {

    @GET("users/{user}/repos")
    Call<List<Repo>> getRepoList(@Path("user") String user);

    @GET("/repos/{user}/{reponame}")
    Call<Repo> getRepoItemDetails(@Path("user") String user, @Path("reponame") String repoName);
}
