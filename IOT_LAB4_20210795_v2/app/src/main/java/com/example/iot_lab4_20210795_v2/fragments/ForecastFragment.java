package com.example.iot_lab4_20210795_v2.fragments;

import android.app.AlertDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    // Sensor Manager
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];
    private boolean puedeMostrarDialogo = true; // Controla si se puede mostrar diálogo
    private final long TIEMPO_ESPERA = 3000; // 5 segundos de espera

    private final Handler handler = new Handler(); // Importante: importar android.os.Handler
    private static final float NOISE = (float) 25; // Umbral de aceleración (25 m/s²)


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

        searchButton.setOnClickListener(v -> {
            String id = locationInput.getText().toString();
            String daysText = daysInput.getText().toString();

            if (id.isEmpty()) {
                Toast.makeText(getContext(), "El ID no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!id.matches("\\d+")) {
                Toast.makeText(getContext(), "El ID solo debe contener números", Toast.LENGTH_SHORT).show();
                return;
            }

            int days;
            if (daysText.isEmpty()) {
                days = 14; // valor por defecto
            } else {
                try {
                    days = Integer.parseInt(daysText);
                    if (days < 1 || days > 14) {
                        Toast.makeText(getContext(), "Los días deben estar entre 1 y 14", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "El número de días debe ser válido", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Llamar a la función con el ID formateado y días
            obtenerPronostico(id, days);
        });

        // Configuración del acelerómetro
        sensorManager = (SensorManager) getContext().getSystemService(getContext().SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        return view;
    }
    private void obtenerPronostico(String location, int days) {
        new WeatherForecastApi().getForecast(location, days, new WeatherForecastApi.ForecastCallback() {
            @Override
            public void onSuccess(ForecastResponse forecastResponse) {
                if (forecastResponse != null && forecastResponse.getForecast() != null) {
                    // Limpiar la lista actual de pronósticos
                    forecastList.clear();

                    // Obtener la información de la ubicación y los días del pronóstico
                    Location locationInfo = forecastResponse.getLocation();
                    List<ForecastDay> forecastDays = forecastResponse.getForecast().getForecastday();

                    // Verificar si los días del pronóstico están disponibles
                    if (forecastDays != null && !forecastDays.isEmpty()) {
                        // Aquí puedes manejar la ubicación si es necesario
                        Log.i("ForecastFragment", "Ubicación: " + locationInfo.getName());

                        // Agregar todos los días del pronóstico a la lista
                        forecastList.addAll(forecastDays);

                        // Actualizar el adaptador con la ubicación y los días del pronóstico
                        adapter = new ForecastAdapter(forecastList, locationInfo);  // Actualizamos con la ubicación
                        recyclerView.setAdapter(adapter);

                        // Actualizar el adaptador
                        adapter.notifyDataSetChanged();
                        // **Registrar el sensor solo si la lista no está vacía**
                        if (sensorManager != null && accelerometer != null) {
                            sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
                        }
                    } else {
                        // Si no hay datos de pronóstico, mostrar un Toast
                        Toast.makeText(getContext(), "No hay datos disponibles para el pronóstico.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si forecastResponse es nulo o no tiene datos válidos
                    Toast.makeText(getContext(), "No se pudo obtener el pronóstico.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("ForecastFragment", "Error al obtener pronóstico: " + errorMessage);
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Sensor Event Listener para detectar el movimiento
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        private final float[] gravity = new float[3];
        private final float[] linear_acceleration = new float[3];
        private final float alpha = 0.8f;

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                // Estimamos la gravedad con filtro paso bajo
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                // Restamos la gravedad para obtener aceleración lineal
                linear_acceleration[0] = event.values[0] - gravity[0];
                linear_acceleration[1] = event.values[1] - gravity[1];
                linear_acceleration[2] = event.values[2] - gravity[2];

                Log.d("SensorData", "Aceleración Lineal: X=" + linear_acceleration[0] + " Y=" + linear_acceleration[1] + " Z=" + linear_acceleration[2]);
                Log.d("SensorData", "Valores del sensor: X=" + event.values[0] + " Y=" + event.values[1] + " Z=" + event.values[2]);
                Log.d("SensorData", "graveda: Xg=" + gravity[0] + " Yg=" + gravity[1] + " Zg=" + gravity[2]);

                float totalAcceleration = (float) Math.sqrt(
                        linear_acceleration[0] * linear_acceleration[0] +
                                linear_acceleration[1] * linear_acceleration[1] +
                                linear_acceleration[2] * linear_acceleration[2]
                );

                if (totalAcceleration > NOISE) {
                    Log.d("SensorData", "Aceleración Total: " + totalAcceleration);
                    mostrarDialogoConfirmacion();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


    // Mostrar el diálogo para confirmar la acción
    private void mostrarDialogoConfirmacion() {
        if (forecastList.isEmpty()) {
            // No mostrar nada si no hay elementos que eliminar
            return;
        }
        if (!puedeMostrarDialogo) return;

        puedeMostrarDialogo = false;

        new AlertDialog.Builder(getContext())
                .setTitle("Confirmar Deshacer Acción")
                .setMessage("¿Deseas eliminar los últimos pronósticos obtenidos?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    forecastList.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Vista de pronósticos limpiada", Toast.LENGTH_SHORT).show();
                    // Detener la escucha de eventos del sensor si la lista está vacía
                    if (forecastList.isEmpty()) {
                        if (sensorManager != null) {
                            sensorManager.unregisterListener(sensorEventListener);
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();

        // Espera 5 segundos para permitir nuevos diálogos
        handler.postDelayed(() -> puedeMostrarDialogo = true, TIEMPO_ESPERA);
    }


    @Override
    public void onPause() {
        super.onPause();
        // Desregistrar el sensor cuando el fragmento se detiene
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }
}