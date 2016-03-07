package com.test.xyz.daggersample1.service.di;

import com.test.xyz.daggersample1.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class, ServiceModule.class})
public interface DaggerGraphComponent extends DaggerGraph {
    
    static final class Initializer {
        private Initializer() {
        }

        public static DaggerGraph init(DaggerApplication app) {
            return DaggerDaggerGraphComponent.builder()
                                             .mainModule(new MainModule(app))
                                             .build();
        }
    }
}
