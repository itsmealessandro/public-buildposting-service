package com.example.DTOs;

import java.util.List;

public class PosterRequest {
  private List<String> selected_zones;
  private double total_price;
  private String request_id;

  // Getter e Setter
  public List<String> getSelected_zones() {
    return selected_zones;
  }

  public void setSelected_zones(List<String> selected_zones) {
    this.selected_zones = selected_zones;
  }

  public double getTotal_price() {
    return total_price;
  }

  public void setTotal_price(double total_price) {
    this.total_price = total_price;
  }

  public String getRequest_id() {
    return request_id;
  }

  public void setRequest_id(String request_id) {
    this.request_id = request_id;
  }
}
