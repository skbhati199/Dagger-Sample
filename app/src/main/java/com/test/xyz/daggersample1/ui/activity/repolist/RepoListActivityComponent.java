package com.test.xyz.daggersample1.ui.activity.repolist;

import com.test.xyz.daggersample1.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {RepoListActivityModule.class}
)
public interface RepoListActivityComponent {
    void inject(RepoListActivity activity);
}

