package com.dheeraj.xfinity.di;

import android.app.Application;

import com.dheeraj.xfinity.XfinityApplication;
import com.dheeraj.xfinity.view.CharactersFragment;
import com.dheeraj.xfinity.view.LandingActivity;
import com.dheeraj.xfinity.view.CharactersDetailFragment;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector<XfinityApplication>{

    Application exposeApplication();
    void inject(LandingActivity landingActivity);
    void inject(CharactersDetailFragment charactersDetailFragment);
    void inject(CharactersFragment charactersFragment);
}
