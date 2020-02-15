package com.vivek.weather.api;


import com.vivek.weather.api.model.currentweather.WeatherData;
import com.vivek.weather.api.model.forecastweather.WeatherForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiServiceInterface {


    @GET("weather")
    Call<WeatherData> getCurrentWeather(@Query("lat") String lat, @Query("lon") String lon,
                                        @Query("appid")String appId, @Query("units") String units);

    @GET("forecast/daily")
    Call<WeatherForecast> getWeatherForecast(@Query("lat") String lat, @Query("lon") String lon,
                                             @Query("cnt") Integer dayCount,  @Query("appid")String appId,
                                             @Query("units") String units);

}
