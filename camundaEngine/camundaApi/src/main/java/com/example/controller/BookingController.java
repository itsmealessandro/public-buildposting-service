package com.example.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingController {

  private final RuntimeService runtimeService;
  private final HistoryService historyService;
  private final String MESSAGE_NAME_START = "client_request";
  private final String MESSAGE_NAME_RESUME = "stop";

  public BookingController(RuntimeService runtimeService, HistoryService historyService) {
    this.runtimeService = runtimeService;
    this.historyService = historyService;
  }

  /*
   * This Method get the user request and puts all the data in execution
   * variables.
   */
  @PostMapping("/api/booking/request")
  public ResponseEntity<?> startBooking(@RequestBody BookingRequest request) {
    System.out.println("########################################################");
    System.out.println("########################################################");
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

    // praticamente impossibile generare collisioni
    String businessKeyUnique = UUID.randomUUID().toString();
    // Avvio processo
    // ----------------------------------------------------------------------------------------------------
    ProcessInstance instance = runtimeService.startProcessInstanceByMessage(MESSAGE_NAME_START, businessKeyUnique,
        variables);

    System.out.println("########################################################");
    System.out.println("########################################################");
    System.out.println("Returning...");
    System.out.println("########################################################");
    System.out.println("########################################################");

    // Recupera output dal processo
    String requestId = (String) runtimeService.getVariable(instance.getId(), "requestId");
    Object selectedZones = runtimeService.getVariable(instance.getId(), "selectedZones");
    Double totalPrice = (Double) runtimeService.getVariable(instance.getId(), "totalPrice");

    System.out.println("Response Format and Data:");
    System.out.println(requestId);
    System.out.println(selectedZones);
    System.out.println(totalPrice);
    System.out.println(businessKeyUnique);

    Map<String, Object> response = new HashMap<>();
    response.put("requestId", requestId);
    response.put("selectedZones", selectedZones);
    response.put("totalPrice", totalPrice);
    response.put("businessKey", businessKeyUnique); // il client dovrà ritornare questo per riprendere il processo

    return ResponseEntity.ok(response);
  }

  @PostMapping("/api/booking/decision")
  public ResponseEntity<DecisionResponse> handleDecision(@RequestBody DecisionRequest request) {

    System.out.println("###################################");
    System.out.println("###################################");
    System.out.println("Decision Request");
    System.out.println("###################################");
    System.out.println("###################################");

    System.out.println(request.toString());

    // Inserimento variabili nel processo Camunda
    Map<String, Object> variables = new HashMap<>();
    variables.put("decision", request.getDecision());

    // Correlare il messaggio al processo in attesa
    runtimeService.correlateMessage(
        MESSAGE_NAME_RESUME,
        request.getBusinessKey(),
        variables);

    // Attendere che il processo finisca (o recuperare variabili storiche)
    // Poiché correlateMessage è sincrono per l'invio, il processo potrebbe impiegare
    // qualche ms a finire.
    // Facciamo un piccolo polling per recuperare le variabili finali.
    String invoiceNumber = "";
    Double amountDue = 0.0;
    String status = request.getDecision().toString();

    try {
      Thread.sleep(500); // Attesa tecnica per permettere al processo di completare
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Recupera variabili dallo storico
    List<HistoricVariableInstance> historicVariables = historyService.createHistoricVariableInstanceQuery()
        .processInstanceId(
            historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(request.getBusinessKey())
                .singleResult().getId())
        .list();

    for (HistoricVariableInstance var : historicVariables) {
      if ("invoiceNumber".equals(var.getName()) && var.getValue() != null) {
        invoiceNumber = var.getValue().toString();
      }
      if ("amountDue".equals(var.getName()) && var.getValue() != null) {
        try {
            amountDue = Double.valueOf(var.getValue().toString());
        } catch (NumberFormatException e) {
            amountDue = 0.0;
        }
      }
    }

    System.out.println("###################################");
    System.out.println("Returning to client");
    System.out.println("Invoice: " + invoiceNumber);
    System.out.println("Amount: " + amountDue);
    System.out.println("###################################");

    return ResponseEntity.ok(new DecisionResponse(
        "Decision processed for request: " + request.getRequestId(),
        invoiceNumber,
        amountDue,
        status));
  }

  @GetMapping("/api/booking/history")
  public ResponseEntity<List<Map<String, Object>>> getHistory() {
      List<HistoricProcessInstance> historicInstances = historyService.createHistoricProcessInstanceQuery()
              .orderByProcessInstanceStartTime().desc()
              .list();

      List<Map<String, Object>> historyList = new ArrayList<>();

      for (HistoricProcessInstance instance : historicInstances) {
          Map<String, Object> historyItem = new HashMap<>();
          historyItem.put("businessKey", instance.getBusinessKey());
          historyItem.put("startTime", instance.getStartTime());
          historyItem.put("endTime", instance.getEndTime());

          List<HistoricVariableInstance> vars = historyService.createHistoricVariableInstanceQuery()
                  .processInstanceId(instance.getId())
                  .list();

          for (HistoricVariableInstance var : vars) {
              historyItem.put(var.getName(), var.getValue());
          }
          historyList.add(historyItem);
      }

      return ResponseEntity.ok(historyList);
  }

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("bookingRequest", new BookingRequest());
    return "home"; // nome del template Thymeleaf
  }

}
