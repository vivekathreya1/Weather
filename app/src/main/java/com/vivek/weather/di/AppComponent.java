package com.vivek.weather.di;

import android.app.Application;

import com.vivek.weather.WeatherApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        AppModule.class, ActivityBuildersModule.class, ViewModelFactoryModule.class,
})
public interface AppComponent extends AndroidInjector<WeatherApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }


}
