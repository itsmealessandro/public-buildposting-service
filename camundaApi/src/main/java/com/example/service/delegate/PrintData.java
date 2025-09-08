package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class PrintData implements JavaDelegate {
  @Override
  public void execute(DelegateExecution execution) {
    System.out.println("#####################################################");
    System.out.println("PRINT");
    System.out.println("#####################################################");
  }
}
