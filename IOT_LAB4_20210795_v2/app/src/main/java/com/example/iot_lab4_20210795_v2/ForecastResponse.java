    package com.example.iot_lab4_20210795_v2;

    import com.example.iot_lab4_20210795_v2.Location.Location;

    public class ForecastResponse {
        private Location location;
        private Current current;
        private Forecast forecast;

        // Getters y setters
        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Current getCurrent() {
            return current;
        }

        public void setCurrent(Current current) {
            this.current = current;
        }

        public Forecast getForecast() {
            return forecast;
        }

        public void setForecast(Forecast forecast) {
            this.forecast = forecast;
        }
    }
