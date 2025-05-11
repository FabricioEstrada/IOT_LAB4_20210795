package com.example.iot_lab4_20210795_v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<Location> locationList;

    public LocationAdapter(List<Location> locationList) {
        this.locationList = locationList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, regionText, countryText, latLonText;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.tvLocationName);
            regionText = itemView.findViewById(R.id.tvRegion);
            countryText = itemView.findViewById(R.id.tvCountry);
            latLonText = itemView.findViewById(R.id.tvLatLon);
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
