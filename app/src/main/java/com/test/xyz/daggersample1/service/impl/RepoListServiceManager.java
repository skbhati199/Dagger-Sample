package com.test.xyz.daggersample1.service.impl;

import android.util.Log;

import com.test.xyz.daggersample1.service.api.RepoListService;

public class RepoListServiceManager implements RepoListService {
    private static final String TAG = RepoListServiceManager.class.getName();

    @Override
    public String[] retrieveRepoList(String userName) {
        String[] values = new String[] {
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        Log.d(TAG, values.length + "");

        return values;
    }

    @Override
    public String retrieveRepoItemDetails(String userName, String projectID) {
        return "Some information ...";
    }
}
