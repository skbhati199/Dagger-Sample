package com.test.xyz.daggersample1.service.di;

import android.app.Application;

public class DaggerApplication extends Application {
    private static DaggerGraph graph;
    private static DaggerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildComponentGraph();
    }

    public static DaggerGraph component() {
        return graph;
    }

    public static void buildComponentGraph() {
        graph = DaggerGraphComponent.Initializer.init(instance);
    }

    /**
     * Visible only for testing purposes.
     */
    public void setTestComponent(DaggerGraph testingComponent) {
        graph = testingComponent;
    }
}
