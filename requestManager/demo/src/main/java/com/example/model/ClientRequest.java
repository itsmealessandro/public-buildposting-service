package com.example.model;

import java.util.HashMap;
import java.util.HashSet;

public class ClientRequest {
  private String username;
  private HashSet<String> cities;
  private HashMap<String, String> max_prices;
  private String poster_format;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public HashSet<String> getCities() {
    return cities;
  }

  public void setCities(HashSet<String> cities) {
    this.cities = cities;
  }

  public String getPoster_format() {
    return poster_format;
  }

  public void setPoster_format(String poster_format) {
    this.poster_format = poster_format;
  }

  @Override
  public String toString() {
    return "ClientRequest [username=" + username + ", cities=" + cities + ", max_prices=" + max_prices
        + ", poster_format=" + poster_format + ", getUsername()=" + getUsername() + ", getCities()=" + getCities()
        + ", getMax_prices()=" + getMax_prices() + ", getPoster_format()=" + getPoster_format() + "]";
  }

  public HashMap<String, String> getMax_prices() {
    return max_prices;
  }

  public void setMax_prices(HashMap<String, String> max_prices) {
    this.max_prices = max_prices;
  }

}
