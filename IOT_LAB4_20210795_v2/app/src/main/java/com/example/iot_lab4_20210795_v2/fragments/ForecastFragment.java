package com.example.iot_lab4_20210795_v2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.iot_lab4_20210795_v2.ArrayAdapterRecycler;
import com.example.iot_lab4_20210795_v2.R;

import java.util.ArrayList;
import java.util.List;

public class ForecastFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText locationInput, daysInput;
    private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        locationInput = view.findViewById(R.id.editLocation);
        daysInput = view.findViewById(R.id.editDays);
        searchButton = view.findViewById(R.id.btnBuscar);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Simulación de datos
        List<String> datos = new ArrayList<>();
        for (int i = 0; i < 10; i++) datos.add("Item " + i);

        recyclerView.setAdapter(new ArrayAdapterRecycler(getContext(), datos));

        return view;
    }
}