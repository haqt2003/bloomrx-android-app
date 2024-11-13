package com.example.bloomrx.response;

public class WeatherResponse {
    private Location location;
    private Current current;

    public Location getLocationResponse() {
        return location;
    }

    public Current getCurrentResponse() {
        return current;
    }
}
