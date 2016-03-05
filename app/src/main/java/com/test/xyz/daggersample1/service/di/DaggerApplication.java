package com.test.xyz.daggersample1.service.di;

import android.app.Application;

public class DaggerApplication extends Application {
    private static DaggerGraphComponent graph;
    private static DaggerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildComponentGraph();
    }

    public static DaggerGraphComponent component() {
        return graph;
    }

    public static void buildComponentGraph() {
        graph = DaggerGraphComponent.Initializer.init(instance);
    }
}
