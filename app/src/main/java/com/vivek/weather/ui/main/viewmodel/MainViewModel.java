package com.vivek.weather.ui.main.viewmodel;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vivek.weather.BuildConfig;
import com.vivek.weather.api.ApiError;
import com.vivek.weather.api.WeatherApiServiceInterface;
import com.vivek.weather.api.model.currentweather.WeatherData;
import com.vivek.weather.api.model.forecastweather.WeatherForecast;
import com.vivek.weather.ui.main.model.CurrentWeather;
import com.vivek.weather.ui.main.model.ForecastWeather;
import com.vivek.weather.utils.MathUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vivek.weather.utils.Constants.ICON_TYPE_PNG;
import static com.vivek.weather.utils.Constants.ICON_URL;
import static com.vivek.weather.utils.Constants.NO_OF_DAYS_FORECAST;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private final WeatherApiServiceInterface mainWebApi;
    public MutableLiveData<CurrentWeather> currentWeatherLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ForecastWeather>> forecastWeatherLiveData = new MutableLiveData<>();
    private CurrentWeather currentWeather;
    private MutableLiveData<Throwable> errorThrowable = new MutableLiveData<>();
    private MutableLiveData<String> errorCode = new MutableLiveData<>();
    private List<ForecastWeather> forecastWeatherList;


    @Inject
    public MainViewModel(WeatherApiServiceInterface mainWebApi, CurrentWeather currentWeather) {
        this.mainWebApi = mainWebApi;
        this.currentWeather = currentWeather;
        forecastWeatherList = new ArrayList<>();
    }

    private void getCurrentTempData(String lat, String lon) {  //TODO- from location services
        mainWebApi.getCurrentWeather(lat, lon, BuildConfig.API_KEY
                , "metric").enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful()) {
                    currentWeather.setCityName(response.body().getName());
                    currentWeather.setDateTime(MathUtils.getDateTimeFromInt(response.body().getDt()));
                    currentWeather.setTemperatureMin(String.valueOf(response.body().getMain().getTempMin().intValue()));
                    currentWeather.setTemperatureMax(String.valueOf(response.body().getMain().getTempMax().intValue()));
                    currentWeather.setTemperature(String.valueOf(response.body().getMain().getTemp().intValue()));
                    currentWeather.setIconUrl(ICON_URL + response.body().getWeather().get(0).getIcon() + ICON_TYPE_PNG);
                    currentWeather.setWeatherDescription(response.body().getWeather().get(0).getMain());
                    long currentTime = Calendar.getInstance().getTime().getTime() / 1000;
                    int sunrise = response.body().getSys().getSunrise();
                    int sunset = response.body().getSys().getSunset();
                    if (currentTime >= sunrise && currentTime < sunset) {
                        currentWeather.setDay(true);
                    } else {
                        currentWeather.setDay(false);
                    }
                    currentWeatherLiveData.setValue(currentWeather);
                    errorThrowable.setValue(null);
                    errorCode.setValue("");
                } else {
                    errorCode.setValue(new ApiError().errorCode(response));
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                errorThrowable.setValue(t);
            }
        });
    }

    public void getData(Location location) {
        if (location == null) {
            errorThrowable.setValue(new Throwable());
        } else {
            String lat = String.valueOf(location.getLatitude());
            String lon = String.valueOf(location.getLongitude());
            getCurrentTempData(lat, lon);
            getForecastTempData();
        }
    }

    private void getForecastTempData() {  //TODO- from location services
        mainWebApi.getWeatherForecast("12.9762", "77.6033", NO_OF_DAYS_FORECAST,
                BuildConfig.API_KEY, "metric").enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {

                if (response.isSuccessful()) {
                    WeatherForecast forecast = response.body();
                    for (int i = 1; i < NO_OF_DAYS_FORECAST; i++) {
                        ForecastWeather forecastWeather = new ForecastWeather();
                        forecastWeather.setDateTime(MathUtils.getDateFromInt(forecast.getList().get(i).getDt()));
                        forecastWeather.setDayOfWeek(MathUtils.getDayFromInt(forecast.getList().get(i).getDt()));
                        forecastWeather.setTemperatureMin(String.valueOf(forecast.getList().get(i).getTemp().getMin().intValue()));
                        forecastWeather.setTemperature(String.valueOf(forecast.getList().get(i).getTemp().getMin().intValue()));
                        forecastWeather.setTemperatureMax(String.valueOf(forecast.getList().get(i).getTemp().getMax().intValue()));
                        forecastWeather.setIconUrl(ICON_URL + forecast.getList().get(i).getWeather().get(0).getIcon() + ICON_TYPE_PNG);
                        forecastWeather.setWeatherDescription(forecast.getList().get(i).getWeather().get(0).getMain());
                        forecastWeatherList.add(forecastWeather);
                    }
                    forecastWeatherLiveData.setValue(forecastWeatherList);
//                    errorThrowable.setValue(null);
//                    errorCode.setValue("");
                } else {
                    errorCode.setValue(new ApiError().errorCode(response));
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<Throwable> getErrorThrowable() {
        return errorThrowable;
    }

    public MutableLiveData<String> getErrorCode() {
        return errorCode;
    }
}
