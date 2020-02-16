package com.vivek.weather.ui.main.model;

public class ForecastWeather extends CurrentWeather {
    private String dayOfWeek;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
