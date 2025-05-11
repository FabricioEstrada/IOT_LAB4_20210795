package com.example.iot_lab4_20210795_v2.weather_api;

import android.util.Log;

import com.example.iot_lab4_20210795_v2.ForecastResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherForecastApi {
    private WeatherApiService weatherApiService;

    public WeatherForecastApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApiService = retrofit.create(WeatherApiService.class);
    }

    // Método para obtener el pronóstico de clima
    public void getForecast(String locationId, int days, final ForecastCallback callback) {
        String locationWithPrefix = "id:" + locationId;  // Concatenar 'id:' al idLocation
        Call<ForecastResponse> call = weatherApiService.getForecast("ec24b1c6dd8a4d528c1205500250305", locationWithPrefix, days);

        Log.i("WeatherForecastApi", "Realizando solicitud a la API para el pronóstico de la ubicación: " + locationId);

        call.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("WeatherForecastApi", "Respuesta exitosa. Datos del pronóstico obtenidos.");
                    response.body().getLocation().setId(Integer.parseInt(locationId));
                    callback.onSuccess(response.body());
                } else {
                    Log.e("WeatherForecastApi", "Error en la respuesta: " + response.code());
                    callback.onFailure("Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                callback.onFailure("Error de conexión: " + t.getMessage());
            }
        });
    }

    public interface ForecastCallback {
        void onSuccess(ForecastResponse forecastResponse);
        void onFailure(String errorMessage);
    }
}
