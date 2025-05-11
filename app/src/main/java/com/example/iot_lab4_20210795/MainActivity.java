package com.example.iot_lab4_20210795;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnIngresar = findViewById(R.id.btnIngresar); // debe coincidir con el ID en XML
        // Validar conexión al iniciar
        if (!isInternetAvailable()) {
            showNoConnectionDialog();
        }

        btnIngresar.setOnClickListener(view -> {
            if (isInternetAvailable()) {
                Intent intent = new Intent(MainActivity.this, AppActivity.class);
                startActivity(intent);
            } else {
                showNoConnectionDialog();
            }
        });
    }
    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean tieneInternet = false;
        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("msg-Internet", "NetworkCapabilities.TRANSPORT_CELLULAR");
                    tieneInternet = true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("msg-Internet", "NetworkCapabilities.TRANSPORT_WIFI");
                    tieneInternet = true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("msg-Internet", "NetworkCapabilities.TRANSPORT_ETHERNET");
                    tieneInternet = true;
                }
            }
        }
        return tieneInternet;
    }
    private void showNoConnectionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sin conexión a Internet")
                .setMessage("Por favor, activa tu conexión a Internet.")
                .setCancelable(false)
                .setPositiveButton("Configuración", (dialog, which) -> {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    Toast.makeText(this, "No puedes continuar sin conexión", Toast.LENGTH_SHORT).show();
                })
                .show();
    }
}