package com.example.iot_lab4_20210795_v2.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_lab4_20210795_v2.Hour;
import com.example.iot_lab4_20210795_v2.Location.Location;
import com.example.iot_lab4_20210795_v2.weather_api.WeatherFutureApi;
import com.example.iot_lab4_20210795_v2.R;
import com.example.iot_lab4_20210795_v2.FutureAdapter;
import com.example.iot_lab4_20210795_v2.weather_api.WeatherFutureApi.FutureForecastCallback;
import com.example.iot_lab4_20210795_v2.ForecastResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FutureFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText locationInput, dayInput;
    private Button searchButton;
    private FutureAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_future, container, false);

        locationInput = view.findViewById(R.id.editLocation);
        dayInput = view.findViewById(R.id.editDay);
        searchButton = view.findViewById(R.id.btnBuscar);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializa el adaptador con una lista vacía
        adapter = new FutureAdapter(new ArrayList<>(),null);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> {
            String locationId = locationInput.getText().toString().trim();
            String date = dayInput.getText().toString().trim();

            // Validar que el ID solo tenga números
            if (!locationId.matches("\\d+")) {
                Toast.makeText(getContext(), "El ID debe contener solo números.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar formato de fecha YYYY-MM-dd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            sdf.setLenient(false);  // Validación estricta
            try {
                sdf.parse(date);  // Intenta parsear
            } catch (ParseException e) {
                Toast.makeText(getContext(), "La fecha debe tener el formato YYYY-MM-dd.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Si pasa ambas validaciones, realiza la búsqueda
            fetchFutureForecast(locationId, date);
        });


        return view;
    }

    private void fetchFutureForecast(String locationId, String date) {
        WeatherFutureApi weatherFutureApi = new WeatherFutureApi();
        weatherFutureApi.getFutureForecast(locationId, date, new FutureForecastCallback() {
            @Override
            public void onSuccess(ForecastResponse forecastResponse) {
                if (forecastResponse != null && forecastResponse.getForecast() != null) {
                    // Obtener la información de la ubicación y las horas del pronóstico futuro
                    Location locationInfo = forecastResponse.getLocation();
                    List<Hour> futureList = forecastResponse.getForecast().getForecastday().get(0).getHour();

                    // Verificar si hay horas del pronóstico disponible
                    if (futureList != null && !futureList.isEmpty()) {
                        // Aquí puedes manejar la ubicación si es necesario
                        Log.i("FutureFragment", "Ubicación: " + locationInfo.getName());

                        // Actualiza los datos en el adaptador
                        adapter.updateData(futureList, locationInfo);  // Actualizamos también la ubicación
                    } else {
                        // Si no hay horas del pronóstico, mostrar un Toast
                        Toast.makeText(getContext(), "No hay datos de pronóstico para esta fecha.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si la respuesta es nula o no contiene los datos esperados
                    Toast.makeText(getContext(), "No se pudo obtener el pronóstico.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("FutureFragment", "Error al obtener pronóstico: " + errorMessage);
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
