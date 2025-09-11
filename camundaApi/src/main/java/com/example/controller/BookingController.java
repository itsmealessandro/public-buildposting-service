package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.BookingRequest;
import com.example.model.DecisionRequest;
import com.example.model.DecisionResponse;

@Controller
public class BookingController {

  private final RuntimeService runtimeService;

  @Autowired
  public BookingController(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  /*
   * This Method get the user request and puts all the data in execution
   * variables.
   */
  @PostMapping("/api/booking/request")
  public ResponseEntity<?> startBooking(
      @RequestParam String username,
      @RequestParam List<String> cities,
      @RequestParam List<Double> maxPrices,
      @RequestParam String format,
      @RequestParam String algorithm) {
    System.out.println("########################################################");
    System.out.println("########################################################");
    System.out.println("REQUEST RECEIVED");
    System.out.println("REQUEST RECEIVED:");
    System.out.println("Username: " + username);
    System.out.println("Cities: " + cities);
    System.out.println("MaxPrices: " + maxPrices);
    System.out.println("Format: " + format);
    System.out.println("Algorithm: " + algorithm);
    System.out.println("########################################################");
    System.out.println("########################################################");
    Map<String, Object> variables = new HashMap<>();
    variables.put("username", username);
    variables.put("client_cities", cities);
    variables.put("format", format);
    variables.put("maxPrices", maxPrices);
    variables.put("algorithm", algorithm);

    // ProcessInstance instance =
    // runtimeService.startProcessInstanceByMessage("client_request", variables);
    ProcessInstance instance = runtimeService.startProcessInstanceByMessage("banana", variables);
    // alternativa con businessKey
    // ProcessInstance instance =
    // runtimeService.startProcessInstanceByMessage(messageName, businessKey,
    // processVariables)

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
    runtimeService.createMessageCorrelation("banana")
        .correlate();

    return ResponseEntity.ok(new DecisionResponse("Decision processed for request: " + request.getRequestId()));
  }

  // GUI controller
  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("title", "Benvenuto!");
    model.addAttribute("message", "Questa è una pagina con TailwindCSS!");
    return "home";
  }

}
