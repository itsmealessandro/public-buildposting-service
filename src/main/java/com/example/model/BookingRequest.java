package com.example.model;

import java.util.List;
import java.util.Map;

public class BookingRequest {
    private String username;
    private List<String> cities;
    private String format;
    private Map<String, Double> maxPrices;
    
    // Aggiungi getter e setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Map<String, Double> getMaxPrices() {
        return maxPrices;
    }

    public void setMaxPrices(Map<String, Double> maxPrices) {
        this.maxPrices = maxPrices;
    }
}