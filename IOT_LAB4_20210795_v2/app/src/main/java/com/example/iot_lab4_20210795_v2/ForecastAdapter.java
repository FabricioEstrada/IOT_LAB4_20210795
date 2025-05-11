package com.example.iot_lab4_20210795_v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_lab4_20210795_v2.Location.Location;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<ForecastDay> forecastList;
    private Location location;  // Agregamos la ubicación para usarla en el adapter

    public ForecastAdapter(List<ForecastDay> forecastList, Location location) {
        this.forecastList = forecastList;
        this.location = location;  // Inicializamos la ubicación
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationNameText, locationIdText, dateText, maxTempText, minTempText, conditionText, aveTempText;

        public ViewHolder(View itemView) {
            super(itemView);
            locationNameText = itemView.findViewById(R.id.tvLocationName);
            locationIdText = itemView.findViewById(R.id.tvLocationId);
            dateText = itemView.findViewById(R.id.tvDate);
            maxTempText = itemView.findViewById(R.id.tvMaxTemp);
            minTempText = itemView.findViewById(R.id.tvMinTemp);
            aveTempText = itemView.findViewById(R.id.tvAveTemp);
            conditionText = itemView.findViewById(R.id.tvCondition);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForecastDay forecastDay = forecastList.get(position);

        // Aquí usamos la ubicación que está fuera de la lista de ForecastDay
        holder.locationNameText.setText(location.getName()); // Nombre de la ubicación
        holder.locationIdText.setText("ID: " + location.getId()); // ID de la ubicación
        holder.dateText.setText("Fecha del pronóstico: " + forecastDay.getDate()); // Fecha del pronóstico
        holder.maxTempText.setText("Máxima temperatura: " + forecastDay.getDay().getMaxtemp_c() + "°C");
        holder.minTempText.setText("Mínima temperatura: " + forecastDay.getDay().getMintemp_c() + "°C");
        holder.aveTempText.setText("Temperatura Promedio: " + forecastDay.getDay().getAvgtemp_c() + "°C");
        holder.conditionText.setText(forecastDay.getDay().getCondition().getText()); // Condición climática
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }
}
