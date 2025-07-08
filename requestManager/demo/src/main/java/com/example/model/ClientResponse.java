package com.example.model;

import java.util.List;

public class ClientResponse {

  private List<String> selectedZones;
  private Integer totalPrice;
  private String requestId;

  public ClientResponse() {
  }

  // Getter e Setter
  public List<String> getSelectedZones() {
    return selectedZones;
  }

  public void setSelectedZones(List<String> selectedZones) {
    this.selectedZones = selectedZones;
  }

  public int getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(int totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  @Override
  public String toString() {
    return "ClientResponse [selectedZones=" + selectedZones + ", totalPrice=" + totalPrice + ", requestId=" + requestId
        + ", getSelectedZones()=" + getSelectedZones() + ", getTotalPrice()=" + getTotalPrice() + ", getRequestId()="
        + getRequestId() + "]";
  }
}
