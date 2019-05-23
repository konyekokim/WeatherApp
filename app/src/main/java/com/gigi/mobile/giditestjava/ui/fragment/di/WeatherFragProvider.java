package com.gigi.mobile.giditestjava.ui.fragment.di;

import com.gigi.mobile.giditestjava.ui.fragment.WeatherFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WeatherFragProvider {

    @ContributesAndroidInjector(modules = {WeatherFragModule.class})
    abstract WeatherFragment contributeMainFragment();

}