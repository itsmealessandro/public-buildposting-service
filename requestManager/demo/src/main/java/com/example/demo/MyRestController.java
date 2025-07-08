package com.example.demo;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
