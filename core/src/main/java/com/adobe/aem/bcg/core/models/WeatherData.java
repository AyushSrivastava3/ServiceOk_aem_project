package com.adobe.aem.bcg.core.models;

public class WeatherData {
    private String city;
    private String description;
    private double temperature;
    private int humidity;

    // Constructor
    public WeatherData(String city, String description, double temperature, int humidity) {
        this.city = city;
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    // Getters and setters
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}
