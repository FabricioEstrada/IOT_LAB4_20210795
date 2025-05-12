package com.example.iot_lab4_20210795_v2.weather_api;

import android.util.Log;

import com.example.iot_lab4_20210795_v2.ForecastResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFutureApi {
    private WeatherApiService weatherApiService;

    public WeatherFutureApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApiService = retrofit.create(WeatherApiService.class);
    }

    // Método para obtener el pronóstico por hora (future.json)
    public void getFutureForecast(String locationId, String date, final FutureForecastCallback callback) {
        String locationWithPrefix = "id:" + locationId;  // Concatenar 'id:' al idLocation
        Call<ForecastResponse> call = weatherApiService.getFutureForecast("ec24b1c6dd8a4d528c1205500250305", locationWithPrefix, date);

        Log.i("WeatherFutureApi", "Realizando solicitud a la API para el pronóstico futuro de la ubicación: " + locationId);

        call.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("WeatherFutureApi", "Respuesta exitosa. Datos del pronóstico futuro obtenidos.");
                    response.body().getLocation().setId(Integer.parseInt(locationId));
                    callback.onSuccess(response.body());
                } else {
                    Log.e("WeatherFutureApi", "Error en la respuesta: " + response.code());
                    callback.onFailure("No hay datos 😔, prueba otro ID o combinación");
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                callback.onFailure("Error de conexión: " + t.getMessage());
            }
        });
    }

    // Callback para manejar la respuesta
    public interface FutureForecastCallback {
        void onSuccess(ForecastResponse forecastResponse);
        void onFailure(String errorMessage);
    }
}
