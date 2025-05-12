package com.example.iot_lab4_20210795_v2;

public class Hour {
    private String time;         // Hora del pronóstico
    private double temp_c;       // Temperatura en grados Celsius
    private double humidity;     // Humedad en porcentaje
    private double chance_of_rain;    // Lluvia en %
    private Condition condition; // Condición del clima (por ejemplo: soleado, nublado)

    // Getters y setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(double temp_c) {
        this.temp_c = temp_c;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getChance_of_rain() {
        return chance_of_rain;
    }

    public void setChance_of_rain(double chance_of_rain) {
        this.chance_of_rain = chance_of_rain;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
