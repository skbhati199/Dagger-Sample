package com.test.xyz.daggersample.view.fragment.main;

import com.test.xyz.daggersample.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {MainFragmentModule.class}
)
public interface MainFragmentComponent {
    void inject(MainFragment mainFragment);
}

