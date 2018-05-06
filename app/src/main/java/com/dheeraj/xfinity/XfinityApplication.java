package com.dheeraj.xfinity;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.dheeraj.xfinity.di.ApplicationComponent;
import com.dheeraj.xfinity.di.ApplicationModule;
import com.dheeraj.xfinity.di.DaggerApplicationComponent;

public class XfinityApplication extends Application{

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

    }

    public ApplicationComponent getApplicationComponent() {
         return applicationComponent;
    }
}
