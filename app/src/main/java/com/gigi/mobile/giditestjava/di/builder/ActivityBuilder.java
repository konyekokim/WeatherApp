package com.gigi.mobile.giditestjava.di.builder;

import com.gigi.mobile.giditestjava.ui.MainActivity;
import com.gigi.mobile.giditestjava.ui.fragment.di.WeatherFragProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {WeatherFragProvider.class})
    abstract MainActivity contributeMainActivity();
}
