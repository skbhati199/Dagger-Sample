package com.test.xyz.daggersample1.ui.fragment.main;

import com.test.xyz.daggersample1.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {MainFragmentModule.class}
)
public interface MainFragmentComponent {
    void inject(MainFragment mainFragment);
}

