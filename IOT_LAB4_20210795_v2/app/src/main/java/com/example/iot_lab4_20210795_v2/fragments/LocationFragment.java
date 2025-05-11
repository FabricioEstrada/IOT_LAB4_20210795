package com.example.iot_lab4_20210795_v2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.iot_lab4_20210795_v2.Location.Location;
import com.example.iot_lab4_20210795_v2.Location.LocationAdapter;
import com.example.iot_lab4_20210795_v2.Location.LocationResponse;
import com.example.iot_lab4_20210795_v2.R;
import com.example.iot_lab4_20210795_v2.weather_api.WeatherLocationApi;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText searchInput;
    private Button searchButton;
    private List<Location> locationList = new ArrayList<>();
    private LocationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        recyclerView = view.findViewById(R.id.rvLocations);
        searchInput = view.findViewById(R.id.editSearch);
        searchButton = view.findViewById(R.id.btnBuscar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crear el adaptador y pasar el listener
        adapter = new LocationAdapter(locationList, new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Location location) {
                // Aquí se pasa el objeto Location cuando se hace clic en el item
                String idLocation = String.valueOf(location.getId());  // Obtener el idLocation

                // Crear un Bundle para pasar el idLocation al ForecastFragment
                Bundle bundle = new Bundle();
                bundle.putString("idLocation", idLocation);

                // Obtener el NavController para realizar la navegación
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_locationFragment_to_forecastFragment, bundle);
            }
        });

        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString();
            if (!query.isEmpty()) {
                buscarUbicaciones(query);
            }
        });

        return view;
    }

    private void buscarUbicaciones(String query) {
        new WeatherLocationApi().getLocations(query, new WeatherLocationApi.WeatherCallback() {
            @Override
            public void onSuccess(List<LocationResponse> locations) {
                locationList.clear();
                for (LocationResponse locationResponse : locations) {
                    // Llenar la lista de ubicaciones con los datos recibidos
                    locationList.add(new Location(locationResponse.getId(), locationResponse.getName(),
                            locationResponse.getRegion(), locationResponse.getCountry(),
                            locationResponse.getLat(), locationResponse.getLon()));
                }
                // Notificar al adaptador que los datos cambiaron
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Manejar el error si la solicitud falla
                Log.e("LocationFragment", "Error al obtener ubicaciones: " + errorMessage);
            }
        });
    }
}
