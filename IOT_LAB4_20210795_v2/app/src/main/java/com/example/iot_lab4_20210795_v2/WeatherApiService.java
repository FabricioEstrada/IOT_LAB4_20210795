package com.example.iot_lab4_20210795_v2;

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
}
