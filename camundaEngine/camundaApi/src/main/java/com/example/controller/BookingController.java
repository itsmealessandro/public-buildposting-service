package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.model.BookingRequest;
import com.example.model.DecisionRequest;
import com.example.model.DecisionResponse;

@Controller
public class BookingController {

  private final RuntimeService runtimeService;

  public BookingController(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  /*
   * This Method get the user request and puts all the data in execution
   * variables.
   */
  @PostMapping("/api/booking/request")
  public ResponseEntity<?> startBooking(@RequestBody BookingRequest request) {
    System.out.println("########################################################");
    System.out.println("########################################################");
    System.out.println("REQUEST RECEIVED");
    System.out.println("REQUEST RECEIVED:");
    System.out.println("Username: " + request.getUsername());
    System.out.println("Cities: " + request.getCities());
    System.out.println("MaxPrices: " + request.getMaxPrices());
    System.out.println("Format: " + request.getFormat());
    System.out.println("Algorithm: " + request.getAlgorithm());
    System.out.println("########################################################");
    System.out.println("########################################################");

    // Inserimento variabili nel processo Camunda
    Map<String, Object> variables = new HashMap<>();
    variables.put("username", request.getUsername());
    variables.put("client_cities", request.getCities());
    variables.put("format", request.getFormat());
    variables.put("maxPrices", request.getMaxPrices());
    variables.put("algorithm", request.getAlgorithm());

    // Avvio processo
    ProcessInstance instance = runtimeService.startProcessInstanceByMessage("client_request", variables);

    // alternativa con businessKey
    // ProcessInstance instance =
    // runtimeService.startProcessInstanceByMessage(messageName, businessKey,
    // processVariables)
    //test comment

    System.out.println("########################################################");
    System.out.println("########################################################");
    System.out.println("Returning...");
    System.out.println("########################################################");
    System.out.println("########################################################");

    // Recupera output dal processo
    String requestId = (String) runtimeService.getVariable(instance.getId(), "requestId");
    Object selectedZones = runtimeService.getVariable(instance.getId(), "selectedZones");
    Double totalPrice = (Double) runtimeService.getVariable(instance.getId(), "totalPrice");

    Map<String, Object> response = new HashMap<>();
    response.put("requestId", requestId);
    response.put("selectedZones", selectedZones);
    response.put("totalPrice", totalPrice);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/api/booking/decision")
  public ResponseEntity<DecisionResponse> handleDecision(@RequestBody DecisionRequest request) {
    // runtimeService.createMessageCorrelation("userDecision")
    // .processInstanceVariableEquals("requestId", request.getRequestId())
    // .setVariable("decision", request.getDecision())
    // .correlate();

    // Correlare il messaggio al processo in attesa
    runtimeService.createMessageCorrelation("client_request")
        .correlate();

    return ResponseEntity.ok(new DecisionResponse("Decision processed for request: " + request.getRequestId()));
  }

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("bookingRequest", new BookingRequest());
    return "home"; // nome del template Thymeleaf
  }

}
