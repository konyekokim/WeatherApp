package com.gigi.mobile.giditestjava.di.component;

import com.gigi.mobile.giditestjava.GidiWeatherApp;
import com.gigi.mobile.giditestjava.di.builder.ActivityBuilder;
import com.gigi.mobile.giditestjava.di.modules.AppModule;
import com.gigi.mobile.giditestjava.di.modules.ContextModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        ContextModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent extends AndroidInjector<GidiWeatherApp> {

    void inject(GidiWeatherApp application);

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<GidiWeatherApp>{}

}
