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
import com.example.iot_lab4_20210795_v2.Location;
import com.example.iot_lab4_20210795_v2.LocationAdapter;
import com.example.iot_lab4_20210795_v2.LocationResponse;
import com.example.iot_lab4_20210795_v2.R;
import com.example.iot_lab4_20210795_v2.WeatherApi;

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
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        recyclerView = view.findViewById(R.id.rvLocations);
        searchInput = view.findViewById(R.id.editSearch);
        searchButton = view.findViewById(R.id.btnBuscar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LocationAdapter(locationList);
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
        new WeatherApi().getLocations(query, new WeatherApi.WeatherCallback() {
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