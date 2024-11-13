package com.example.bloomrx.Retrofit;

import com.example.bloomrx.api.WeatherService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherApiService {

    private static String BASE_URL = "https://api.weatherapi.com/v1/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static WeatherService getWeatherService() {
        return getRetrofitInstance().create(WeatherService.class);
    }
}
