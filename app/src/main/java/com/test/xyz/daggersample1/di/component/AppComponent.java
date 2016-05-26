package com.test.xyz.daggersample1.di.component;

import com.test.xyz.daggersample1.di.ServiceModule;
import com.test.xyz.daggersample1.di.module.AppModule;
import com.test.xyz.daggersample1.ui.activity.repodetails.RepoDetailsActivityComponent;
import com.test.xyz.daggersample1.ui.activity.repodetails.RepoDetailsActivityModule;
import com.test.xyz.daggersample1.ui.fragment.main.MainFragmentComponent;
import com.test.xyz.daggersample1.ui.fragment.main.MainFragmentModule;
import com.test.xyz.daggersample1.ui.fragment.repolist.RepoListFragmentComponent;
import com.test.xyz.daggersample1.ui.fragment.repolist.RepoListFragmentModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ServiceModule.class})
public interface AppComponent {

    MainFragmentComponent plus(MainFragmentModule module);
    RepoListFragmentComponent plus(RepoListFragmentModule module);
    RepoDetailsActivityComponent plus(RepoDetailsActivityModule module);
}
