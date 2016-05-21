package com.test.xyz.daggersample1.di.component;

import com.test.xyz.daggersample1.di.ServiceModule;
import com.test.xyz.daggersample1.di.module.AppModule;
import com.test.xyz.daggersample1.ui.activity.main.MainActivityComponent;
import com.test.xyz.daggersample1.ui.activity.main.MainActivityModule;
import com.test.xyz.daggersample1.ui.activity.repodetails.RepoDetailsActivityComponent;
import com.test.xyz.daggersample1.ui.activity.repodetails.RepoDetailsActivityModule;
import com.test.xyz.daggersample1.ui.activity.repolist.RepoListActivityComponent;
import com.test.xyz.daggersample1.ui.activity.repolist.RepoListActivityModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ServiceModule.class})
public interface AppComponent {

    MainActivityComponent plus(MainActivityModule module);

    RepoListActivityComponent plus(RepoListActivityModule module);
    RepoDetailsActivityComponent plus(RepoDetailsActivityModule module);
}
