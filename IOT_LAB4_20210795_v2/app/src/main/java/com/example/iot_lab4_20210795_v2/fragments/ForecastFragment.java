package com.example.iot_lab4_20210795_v2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.iot_lab4_20210795_v2.ArrayAdapterRecycler;
import com.example.iot_lab4_20210795_v2.ForecastAdapter;
import com.example.iot_lab4_20210795_v2.ForecastDay;
import com.example.iot_lab4_20210795_v2.ForecastResponse;
import com.example.iot_lab4_20210795_v2.Location.Location;
import com.example.iot_lab4_20210795_v2.R;
import com.example.iot_lab4_20210795_v2.weather_api.WeatherForecastApi;

import java.util.ArrayList;
import java.util.List;

public class ForecastFragment extends Fragment {

    private RecyclerView recyclerView;
    private ForecastAdapter adapter;
    private List<ForecastDay> forecastList = new ArrayList<>();
    private EditText locationInput, daysInput;
    private Button searchButton;
    private String idLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        locationInput = view.findViewById(R.id.editLocation);
        daysInput = view.findViewById(R.id.editDays);
        searchButton = view.findViewById(R.id.btnBuscar);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ForecastAdapter(forecastList,null);
        recyclerView.setAdapter(adapter);

        // Obtener el idLocation desde el Bundle
        Bundle args = getArguments();
        if (args != null && args.containsKey("idLocation")) {
            idLocation = args.getString("idLocation");
            obtenerPronostico(idLocation, 14);
        } else {
            Log.e("ForecastFragment", "No se recibió el idLocation");
        }

        return view;
    }
    private void obtenerPronostico(String location, int days) {
        new WeatherForecastApi().getForecast(location, days, new WeatherForecastApi.ForecastCallback() {
            @Override
            public void onSuccess(ForecastResponse forecastResponse) {
                if (forecastResponse != null) {
                    // Limpiar la lista actual de pronósticos
                    forecastList.clear();

                    // Obtener la información de la ubicación y los días del pronóstico
                    Location locationInfo = forecastResponse.getLocation();
                    List<ForecastDay> forecastDays = forecastResponse.getForecast().getForecastday();

                    // Aquí puedes manejar la ubicación si es necesario
                    Log.i("ForecastFragment", "Ubicación: " + locationInfo.getName());

                    // Agregar todos los días del pronóstico a la lista
                    forecastList.addAll(forecastDays);

                    // Actualizar el adaptador con la ubicación y los días del pronóstico
                    adapter = new ForecastAdapter(forecastList, locationInfo);  // Actualizamos con la ubicación
                    recyclerView.setAdapter(adapter);

                    // Actualizar el adaptador
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("ForecastFragment", "Error al obtener pronóstico: " + errorMessage);
            }
        });
    }
}