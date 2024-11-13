package com.example.bloomrx;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bloomrx.Retrofit.WeatherApiService;
import com.example.bloomrx.response.WeatherResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class Home extends AppCompatActivity {

    private static final String API_URL = "https://api.weatherapi.com/v1/current.json?key=1016bcbb8c984b29bc091718242910&q=Hanoi&lang=vi";

    private TextView location;
    private TextView date;
    private TextView temp;
    private TextView humidity;
    private TextView wind;
    private TextView rain;
    private ImageView user;
    private ImageView camera;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Call<WeatherResponse> weatherResponseCall = WeatherApiService.getWeatherService().getWeather();
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, retrofit2.Response<WeatherResponse> response) {
                if(response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();

                    assert weatherResponse != null;
                    humidity.setText(weatherResponse.getCurrentResponse().getHumidity() + "%");
                    String tempString = weatherResponse.getCurrentResponse().getTemp_c();
                    int tempInt = (int) Double.parseDouble(tempString);
                    String tempIntString = String.valueOf(tempInt);
                    temp.setText(tempIntString);
                    rain.setText(weatherResponse.getCurrentResponse().getPrecip_mm() + " mm");
                    wind.setText(weatherResponse.getCurrentResponse().getWind_kph() + " kph");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("API Error", "Request failed: " + t.getMessage());
            }
        });

        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng trong Calendar bắt đầu từ 0, nên phải cộng thêm 1
        int year = calendar.get(Calendar.YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Lấy thứ trong tuần (1 = Chủ nhật, 7 = Thứ 7)

        // Chuyển đổi số thứ trong tuần thành tên ngày
        String dayOfWeekName = "";
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                dayOfWeekName = "CN";
                break;
            case Calendar.MONDAY:
                dayOfWeekName = "Th 2";
                break;
            case Calendar.TUESDAY:
                dayOfWeekName = "Th 3";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeekName = "Th 4";
                break;
            case Calendar.THURSDAY:
                dayOfWeekName = "Th 5";
                break;
            case Calendar.FRIDAY:
                dayOfWeekName = "Th 6";
                break;
            case Calendar.SATURDAY:
                dayOfWeekName = "Th 7";
                break;
        }

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        location = findViewById(R.id.location);
        date = findViewById(R.id.date);
        temp = findViewById(R.id.temp);
        humidity = findViewById(R.id.humidity);
        wind = findViewById(R.id.wind);
        rain = findViewById(R.id.rain);
        user = findViewById(R.id.user);
        camera = findViewById(R.id.camera);

        date.setText(dayOfWeekName + ", " + dayOfMonth + " thg " + month);

        if(mUser == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Profile.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}