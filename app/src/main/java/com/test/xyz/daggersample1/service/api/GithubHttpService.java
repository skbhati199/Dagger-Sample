package com.test.xyz.daggersample1.service.api;

import com.test.xyz.daggersample1.service.api.model.Repo;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GithubHttpService {

    @GET("users/{user}/repos")
    Observable<List<Repo>> getRepoList(@Path("user") String user);

    @GET("/repos/{user}/{reponame}")
    Observable<Repo> getRepoItemDetails(@Path("user") String user, @Path("reponame") String repoName);
}
