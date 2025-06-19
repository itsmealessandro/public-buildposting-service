package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTOs.PosterRequest;
import com.example.DTOs.PosterResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api")
public class ControllerSample {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @PostMapping(value = "/poster-request", consumes = "application/json")
  public ResponseEntity<PosterResponse> handlePosterRequest(@RequestBody PosterRequest request) {
    // 1. Stampa JSON ricevuto
    try {
      String receivedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
      System.out.println("📥 JSON ricevuto:\n" + receivedJson);

      // 2. Costruzione risposta
      PosterResponse response = new PosterResponse("Request handled successfully", "ok");

      // 3. Stampa JSON di risposta
      String responseJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
      System.out.println("📤 JSON di risposta:\n" + responseJson);

      // 4. Invio risposta
      return ResponseEntity.ok(response);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }

  }

}
