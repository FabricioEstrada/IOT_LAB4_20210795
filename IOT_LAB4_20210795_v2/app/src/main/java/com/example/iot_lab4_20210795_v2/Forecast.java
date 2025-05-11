package com.example.iot_lab4_20210795_v2;

import java.util.List;

public class Forecast {
    private List<ForecastDay> forecastday;

    // Getter y setter para forecastday
    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<ForecastDay> forecastday) {
        this.forecastday = forecastday;
    }
}
