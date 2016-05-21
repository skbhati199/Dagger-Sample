package com.test.xyz.daggersample1.ui.activity.repodetails;

import com.test.xyz.daggersample1.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {RepoDetailsActivityModule.class}
)
public interface RepoDetailsActivityComponent {
    void inject(RepoDetailsActivity activity);
}

