package com.vivek.weather.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vivek.weather.api.ApiError;
import com.vivek.weather.api.WeatherApiServiceInterface;
import com.vivek.weather.api.model.currentweather.WeatherData;
import com.vivek.weather.api.model.forecastweather.WeatherForecast;
import com.vivek.weather.model.CurrentWeather;
import com.vivek.weather.utils.MathUtils;

import java.util.Calendar;

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
    private CurrentWeather currentWeather;
    private MutableLiveData<Throwable> errorThrowable = new MutableLiveData<>();
    private MutableLiveData<String> errorCode = new MutableLiveData<>();


    @Inject
    public MainViewModel(WeatherApiServiceInterface mainWebApi, CurrentWeather currentWeather) {
        this.mainWebApi = mainWebApi;
        this.currentWeather = currentWeather;
        getData();
    }

    private void getCurrentTempData() {  //TODO- from location services
        mainWebApi.getCurrentWeather("12.9762", "77",
                "b449b024cea38b4f5b1f8dc0fd043ad4", "metric").enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful()) {
                    currentWeather.setCityName(response.body().getName());
                    currentWeather.setDateTime(MathUtils.getDateFromInt(response.body().getDt()));
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
                   errorCode.setValue( new ApiError().errorCode(response));
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                errorThrowable.setValue(t);
//                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void getData() {
        getCurrentTempData();
//        getForecastTempData();
    }

    private void getForecastTempData() {  //TODO- from location services
        mainWebApi.getWeatherForecast("12.9762", "77.6033", NO_OF_DAYS_FORECAST,
                "b449b024cea38b4f5b1f8dc0fd043ad4", "metric").enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.e(TAG, "onResponse: " + response.body().getList().get(0).getTemp().getDay());
                Log.e(TAG, "onResponse: " + response.body().getList().get(0).getTemp().getNight());
                Log.e(TAG, "onResponse: " + response.body().getList().get(0).getTemp().getMorn());
                Log.e(TAG, "onResponse: " + response.body().getList().get(0).getTemp().getEve());
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
