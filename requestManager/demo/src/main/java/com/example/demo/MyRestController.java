package com.example.demo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.ClientRequest;
import com.example.model.ClientResponse;

@RestController
public class MyRestController {

  @Autowired
  private RuntimeService runtimeService;

  /**
   * This method handles GET requests to the "/message-1" endpoint.
   * When invoked, it correlates the "message-1" message with a BPMN process
   * and injects a variable at process startup.
   */
  @GetMapping("message-1") // Maps this method to GET requests on "/message-1"
  public void getMessageOne() {
    // Creates a correlation for the message named "message-1"
    runtimeService
        .createMessageCorrelation("message-1")
        // Injects a variable named "InputVariable" with a specific value
        // This variable can be used inside the BPMN process
        .setVariable("InputVariable", "Input Variable Value for greeting")
        // Executes the correlation, which will start a BPMN process instance
        // that has a "Message Start Event" waiting for "message-1"
        .correlate();
    System.out.println("Message 'message-1' correlated. BPMN process started.");
    // You can add logic here to respond to the HTTP request,
    // for example, by sending a success message.
  }

  @PostMapping("poster-request")
  public ClientResponse handleRequest(@RequestBody ClientRequest clientRequest) {
    System.out.println("###################### request received ###################### ");
    System.out.println(clientRequest);
    // NOTE: service call

    System.out.println("###################### request responding ###################### ");
    // Esempio: calcolo dei dati di risposta
    List<String> selectedZones = List.of("Zone1", "Zone2"); // Dummy data
    HashMap<String, Integer> pricesMap = clientRequest.getMax_prices();
    Collection<Integer> prices = pricesMap.values();
    Integer totalPrice = 0;

    for (Integer price : prices) {
      totalPrice += price;
    }
    String requestId = UUID.randomUUID().toString();

    ClientResponse clientResponse = new ClientResponse();
    clientResponse.setSelectedZones(selectedZones);
    clientResponse.setTotalPrice(totalPrice);
    clientResponse.setRequestId(requestId);

    System.out.println(clientResponse);

    return clientResponse;
  }
}
