package com.test.xyz.daggersample1.service.di;

import com.test.xyz.daggersample1.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class})
public interface DaggerGraphComponent {
    void inject(MainActivity mainActivity);

    static final class Initializer {
        private Initializer() {
        }

        public static DaggerGraphComponent init(DaggerApplication app) {
            return DaggerDaggerGraphComponent.builder()
                                             .mainModule(new MainModule(app))
                                             .build();
        }
    }
}
