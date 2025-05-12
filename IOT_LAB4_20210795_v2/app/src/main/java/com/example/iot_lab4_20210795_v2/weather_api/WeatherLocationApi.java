package com.example.iot_lab4_20210795_v2.weather_api;

import android.util.Log;

import com.example.iot_lab4_20210795_v2.Location.LocationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherLocationApi {

    private WeatherApiService weatherApiService;

    public WeatherLocationApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApiService = retrofit.create(WeatherApiService.class);
    }

    // Método para obtener las ubicaciones
    public void getLocations(String query, final WeatherCallback callback) {
        Call<List<LocationResponse>> call = weatherApiService.getLocations("ec24b1c6dd8a4d528c1205500250305", query);

        Log.i("WeatherApi", "Realizando solicitud a la API para la ubicación: " + query);

        // Llamada asíncrona a la API
        call.enqueue(new Callback<List<LocationResponse>>() {
            @Override
            public void onResponse(Call<List<LocationResponse>> call, Response<List<LocationResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Si la respuesta es exitosa
                    Log.i("WeatherApi", "Respuesta exitosa. Número de ubicaciones obtenidas: " + response.body().size());
                    callback.onSuccess(response.body());
                } else {
                    // Si hubo un error
                    Log.e("WeatherApi", "Error en la respuesta: " + response.code());
                    callback.onFailure("No hay datos 😔, prueba otro ID o combinación");
                }
            }

            @Override
            public void onFailure(Call<List<LocationResponse>> call, Throwable t) {
                // Si hubo un error en la conexión
                callback.onFailure("Error de conexión: " + t.getMessage());
            }
        });
    }

    // Interfaz para manejar la respuesta de la API
    public interface WeatherCallback {
        void onSuccess(List<LocationResponse> locations);
        void onFailure(String errorMessage);
    }
}
