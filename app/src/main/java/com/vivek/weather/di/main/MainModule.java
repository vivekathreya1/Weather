package com.vivek.weather.di.main;

import com.vivek.weather.api.WeatherApiServiceInterface;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module
public class MainModule {

    @MainScope
    @Provides
    static WeatherApiServiceInterface provideRetrofit(Retrofit retrofit){
        return retrofit.create(WeatherApiServiceInterface.class);
    }
}
