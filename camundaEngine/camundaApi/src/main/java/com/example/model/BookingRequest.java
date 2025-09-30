package com.example.model;

import java.util.List;
import java.util.Map;

public class BookingRequest {
  private String username;
  private List<String> cities;
  private String format;
  private Map<String, Double> maxPrices;
  private String algorithm;

  public String getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

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

  @Override
  public String toString() {
    return "BookingRequest [username=" + username + ", cities=" + cities + ", format=" + format + ", maxPrices="
        + maxPrices + ", algorithm=" + algorithm + ", getAlgorithm()=" + getAlgorithm() + ", getUsername()="
        + getUsername() + ", getCities()=" + getCities() + ", getFormat()=" + getFormat() + ", getMaxPrices()="
        + getMaxPrices() + "]";
  }

}
