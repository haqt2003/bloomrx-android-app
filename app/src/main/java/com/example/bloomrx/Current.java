package com.example.bloomrx;

public class Current {
    private String temp_c;   // Nhiệt độ (Celsius)
    private String wind_kph; // Tốc độ gió (km/h)
    private String precip_mm; // Lượng mưa (mm)
    private String humidity;    // Độ ẩm (%)

    // Getter and Setter methods
    public String getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(String temp_c) {
        this.temp_c = temp_c;
    }

    public String getWind_kph() {
        return wind_kph;
    }

    public void setWind_kph(String wind_kph) {
        this.wind_kph = wind_kph;
    }

    public String getPrecip_mm() {
        return precip_mm;
    }

    public void setPrecip_mm(String precip_mm) {
        this.precip_mm = precip_mm;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
