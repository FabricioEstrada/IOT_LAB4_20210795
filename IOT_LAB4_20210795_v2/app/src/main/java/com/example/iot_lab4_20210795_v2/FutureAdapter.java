package com.example.iot_lab4_20210795_v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.iot_lab4_20210795_v2.Location.Location;

import java.util.List;

public class FutureAdapter extends RecyclerView.Adapter<FutureAdapter.FutureViewHolder> {

    private List<Hour> futureList;
    private Location location;  // Aquí se recibe el objeto Location o cualquier otro dato adicional

    public FutureAdapter(List<Hour> futureList, Location location) {
        this.futureList = futureList;
        this.location = location;  // Guardamos la ubicación (o el ID) en el adaptador
    }

    @NonNull
    @Override
    public FutureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_future, parent, false);
        return new FutureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FutureViewHolder holder, int position) {
        Hour hour = futureList.get(position);

        // Aquí extraemos los valores de la clase Hour
        holder.tvLocation.setText(location.getName() + " - " + location.getId());
        holder.tvHour.setText("Hora: " + hour.getTime().split(" ")[1]);
        holder.tvTemp.setText("Temp: " + hour.getTemp_c() + "°C");
        holder.tvCondition.setText("Clima: " + hour.getCondition().getText());
        holder.tvHumidity.setText("Humedad: " + hour.getHumidity() + "%");
        holder.tvRain.setText("Lluvia: " + hour.getChance_of_rain() + "%");

        // Usamos Glide para cargar el ícono del clima
        Glide.with(holder.itemView.getContext())
                .load("https:" + hour.getCondition().getIcon())  // Asegúrate de que el ícono tenga la URL correcta
                .into(holder.ivIcon);
    }

    @Override
    public int getItemCount() {
        return futureList.size();
    }

    public void updateData(List<Hour> newList, Location location) {
        this.futureList = newList;
        this.location = location;
        notifyDataSetChanged();
    }

    static class FutureViewHolder extends RecyclerView.ViewHolder {
        TextView tvHour, tvTemp, tvCondition, tvHumidity, tvRain, tvLocation;  // Añadido tvLocation para mostrar la ubicación
        ImageView ivIcon;

        public FutureViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHour = itemView.findViewById(R.id.tvHour);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            tvCondition = itemView.findViewById(R.id.tvCondition);
            tvLocation = itemView.findViewById(R.id.tvLocationName);
            tvHumidity = itemView.findViewById(R.id.tvHumidity);
            tvRain = itemView.findViewById(R.id.tvRain);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }
}
