package com.example.DTOs;

public class PosterResponse {
  private String message;
  private String status;

  public PosterResponse(String message, String status) {
    this.message = message;
    this.status = status;
  }

  // Getter e Setter
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
