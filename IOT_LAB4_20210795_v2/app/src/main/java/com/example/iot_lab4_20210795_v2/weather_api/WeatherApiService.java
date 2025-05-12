package com.example.iot_lab4_20210795_v2.weather_api;

import com.example.iot_lab4_20210795_v2.ForecastResponse;
import com.example.iot_lab4_20210795_v2.Location.LocationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    @GET("search.json")
    Call<List<LocationResponse>> getLocations(
            @Query("key") String apiKey,
            @Query("q") String query
    );

    @GET("forecast.json")
    Call<ForecastResponse> getForecast(
            @Query("key") String apiKey,
            @Query("q") String idlocation,
            @Query("days") int days
    );

    @GET("future.json")
    Call<ForecastResponse> getFutureForecast(
            @Query("key") String apiKey,
            @Query("q") String idLocation,
            @Query("dt") String futureDate
    );


}
