package com.example.iot_lab4_20210795_v2.Location;

public class Location {
    private int id;
    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;
    private String url;

    public Location(int id, String name, String region, String country, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getRegion() { return region; }
    public String getCountry() { return country; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public String getUrl() { return url; }

    public void setId(int id) { this.id = id; }
}
