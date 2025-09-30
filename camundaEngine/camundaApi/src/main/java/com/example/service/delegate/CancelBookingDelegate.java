package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.util.SoapClient;

@Component
public class CancelBookingDelegate implements JavaDelegate {
  @Autowired
  private SoapClient soapClient;

  @Override
  public void execute(DelegateExecution execution) {
    String requestId = (String) execution.getVariable("requestId");
    soapClient.cancelBooking(requestId);
  }
}
