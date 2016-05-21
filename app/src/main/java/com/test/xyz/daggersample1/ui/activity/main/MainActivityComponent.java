package com.test.xyz.daggersample1.ui.activity.main;

import com.test.xyz.daggersample1.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {MainActivityModule.class}
)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}

