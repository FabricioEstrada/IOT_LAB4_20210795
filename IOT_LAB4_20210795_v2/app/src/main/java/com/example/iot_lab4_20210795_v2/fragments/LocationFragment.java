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

public class LocationFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText searchInput;
    private Button searchButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchInput = view.findViewById(R.id.editSearch);
        searchButton = view.findViewById(R.id.btnBuscar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Simula datos
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) items.add("Item " + i);

        recyclerView.setAdapter(new ArrayAdapterRecycler(getContext(), items));

        return view;
    }
}