package com.test.xyz.daggersample.di.component;

import com.test.xyz.daggersample.di.module.AppModule;
import com.test.xyz.daggersample.view.activity.repodetails.RepoDetailsActivityComponent;
import com.test.xyz.daggersample.view.activity.repodetails.RepoDetailsActivityModule;
import com.test.xyz.daggersample.view.fragment.main.MainFragmentComponent;
import com.test.xyz.daggersample.view.fragment.main.MainFragmentModule;
import com.test.xyz.daggersample.view.fragment.repolist.RepoListFragmentComponent;
import com.test.xyz.daggersample.view.fragment.repolist.RepoListFragmentModule;
import com.test.xyz.daggersample.di.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ServiceModule.class})
public interface AppComponent {

    MainFragmentComponent plus(MainFragmentModule module);
    RepoListFragmentComponent plus(RepoListFragmentModule module);
    RepoDetailsActivityComponent plus(RepoDetailsActivityModule module);
}
