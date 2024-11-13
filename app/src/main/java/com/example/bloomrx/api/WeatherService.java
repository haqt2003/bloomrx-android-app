package com.example.bloomrx.api;

import com.example.bloomrx.response.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherService {
    @GET("current.json?key=1016bcbb8c984b29bc091718242910&q=Hanoi&lang=vi")
    Call<WeatherResponse> getWeather();
}
