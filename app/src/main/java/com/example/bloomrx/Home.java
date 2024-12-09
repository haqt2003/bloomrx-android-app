package com.example.bloomrx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bloomrx.retrofit.WeatherApiService;
import com.example.bloomrx.response.WeatherResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class Home extends AppCompatActivity {

    private static final String API_URL = "https://api.weatherapi.com/v1/current.json?key=1016bcbb8c984b29bc091718242910&q=Hanoi&lang=vi";

    private TextView location;
    private TextView date;
    private TextView temp;
    private TextView humidity;
    private TextView wind;
    private TextView rain;
    private ImageView user;
    private ImageView album;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 101;

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
        album = findViewById(R.id.album);

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
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "ok", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}