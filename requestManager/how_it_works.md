# model - API explaination

This file has the goal to explain the interaction between the camunda bpmn process and the Spring API.

## Camunda model "mainStart.bpmn"

This camunda bpmn model has to be deployed when the SPRING API is already running.
When it is deployed the it connects to the spring application, in particular to the "message-1" Get request.

## Demo spring application

This application is a simple Spring Boot application with REST and Camunda dependencies.
In particular the controller has this method:

``` java

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
```

As you can see the comments defines the behavior.
