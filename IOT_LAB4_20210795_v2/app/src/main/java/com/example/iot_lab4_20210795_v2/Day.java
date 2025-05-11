package com.example.iot_lab4_20210795_v2;

public class Day {
    private double maxtemp_c;
    private double mintemp_c;
    private Condition condition;

    // Getter y setter para maxtemp_c
    public double getMaxtemp_c() {
        return maxtemp_c;
    }

    public void setMaxtemp_c(double maxtemp_c) {
        this.maxtemp_c = maxtemp_c;
    }

    // Getter y setter para mintemp_c
    public double getMintemp_c() {
        return mintemp_c;
    }

    public void setMintemp_c(double mintemp_c) {
        this.mintemp_c = mintemp_c;
    }

    // Getter y setter para condition
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    // getters y setters
}
