package com.adobe.aem.bcg.core.Service;

import com.adobe.aem.bcg.core.models.WeatherData;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);
    private static final String API_KEY = "fa36ec6e22b67bf704cc56c9c13f6b1b"; // Replace with your API key
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    public WeatherData getWeatherData(String city) throws IOException {
        String description = "Sunny";
        double temperature = 25.0;
        int humidity = 50;
        try {
            URL url = new URL(String.format(API_URL, city, API_KEY));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            String response = IOUtils.toString(inputStream, "UTF-8");

            JSONObject jsonObject = new JSONObject(response);

            description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
            temperature = jsonObject.getJSONObject("main").getDouble("temp") - 273.15; // Convert Kelvin to Celsius
            humidity = jsonObject.getJSONObject("main").getInt("humidity");

            LOGGER.info("Weather Data Received: Description={}, Temperature={}, Humidity={}", description, temperature, humidity);
        } catch (IOException | JSONException e) {
            LOGGER.error("Error fetching weather data: {}", e.getMessage());
        }

        return new WeatherData(city, description, temperature, humidity);
    }
}
