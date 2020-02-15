package com.vivek.weather.di;

import com.vivek.weather.di.main.MainFragmentBuildersModule;
import com.vivek.weather.di.main.MainModule;
import com.vivek.weather.di.main.MainScope;
import com.vivek.weather.di.main.MainViewModelModule;
import com.vivek.weather.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {


    @MainScope
    @ContributesAndroidInjector(modules = {MainFragmentBuildersModule.class,
            MainViewModelModule.class, MainModule.class})
    abstract MainActivity contributeMainActivity();
}
