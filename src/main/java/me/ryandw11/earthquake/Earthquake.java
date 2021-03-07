package me.ryandw11.earthquake;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Representation of an Earthquake.
 */
public class Earthquake {
    private String place;
    private double magnitude;
    private long time;
    private int tsunami;
    private double longitude;
    private double latitude;

    public Earthquake(String place, double magnitude, double longitude, double latitude) {
        this.place = place;
        this.magnitude = magnitude;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = System.currentTimeMillis();
        this.tsunami = 0;
    }

    public Earthquake(JSONObject properties, JSONObject geometry) {
        this.place = properties.getString("place");
        this.magnitude = properties.getDouble("mag");
        this.time = properties.getLong("time");
        this.tsunami = properties.getInt("tsunami");

        JSONArray coords = geometry.getJSONArray("coordinates");
        this.longitude = coords.getDouble(0);
        this.latitude = coords.getDouble(1);
    }

    public String getPlace() {
        return place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public long getTime() {
        return time;
    }

    public int getTsunami() {
        return tsunami;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Earthquake)) return false;
        Earthquake other = (Earthquake) obj;
        return other.place.equals(place) && other.magnitude == magnitude && other.time == time
                && other.tsunami == tsunami && other.longitude == longitude && other.latitude == latitude;
    }
}
