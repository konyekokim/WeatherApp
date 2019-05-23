package com.gigi.mobile.giditestjava.di.modules;

import android.content.Context;

import com.gigi.mobile.giditestjava.GidiWeatherApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    @Singleton
    @Provides
    Context provideContext(GidiWeatherApp application) {
        return application;
    }

}
