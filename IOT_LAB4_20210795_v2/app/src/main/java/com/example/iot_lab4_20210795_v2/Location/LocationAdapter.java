package com.example.iot_lab4_20210795_v2.Location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_lab4_20210795_v2.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<Location> locationList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Location location);
    }

    public LocationAdapter(List<Location> locationList, OnItemClickListener listener) {
        this.locationList = locationList;
        this.listener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, regionText, countryText, latLonText;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.tvLocationName);
            regionText = itemView.findViewById(R.id.tvRegion);
            countryText = itemView.findViewById(R.id.tvCountry);
            latLonText = itemView.findViewById(R.id.tvLatLon);
            // Establecer el click listener aquí
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition(); // Obtén la posición del elemento
                if (position != RecyclerView.NO_POSITION) { // Verifica que la posición sea válida
                    Location location = locationList.get(position);
                    if (listener != null && location != null) {
                        listener.onItemClick(location);  // Llamar al listener con el objeto Location
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        Location location = locationList.get(position);
        holder.nameText.setText(location.getName());
        holder.regionText.setText("Región: " + location.getRegion());
        holder.countryText.setText("País: " + location.getCountry());
        holder.latLonText.setText("Lat: " + location.getLat() + ", Lon: " + location.getLon());
    }


    @Override
    public int getItemCount() {
        return locationList.size();
    }
}
