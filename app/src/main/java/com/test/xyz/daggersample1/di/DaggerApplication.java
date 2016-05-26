package com.test.xyz.daggersample1.di;

import android.app.Application;
import android.content.Context;

import com.test.xyz.daggersample1.di.component.AppComponent;
import com.test.xyz.daggersample1.di.component.DaggerAppComponent;
import com.test.xyz.daggersample1.di.module.AppModule;

//import com.test.xyz.daggersample1.di.component.CustomComponent;

public class DaggerApplication extends Application {
    private static AppComponent appComponent;
    //private static CustomComponent customComponent;
    private static DaggerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initAppComponents();
    }

    public static DaggerApplication get(Context context) {
        return (DaggerApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    //public CustomComponent getCustomComponent() {
    //    return customComponent;
    //}

    public void initAppComponents() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    /*
    public CustomComponent createCustomComponent(CustomModule customModule) {
        customComponent = appComponent.plus(customModule);
        return customComponent;
    }

    public void releaseCustomComponent() {
        customComponent = null;
    }
    */

    /**
     * Visible only for testing purposes.
     */
    public void setTestComponent(AppComponent testingComponent) {
        appComponent = testingComponent;
    }
}
