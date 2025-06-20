package com.example.service.delegate;

import java.io.FileWriter;
import java.io.IOException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.util.SoapClient;

@Component
public class ConfirmBookingDelegate implements JavaDelegate {

    @Autowired
    private SoapClient soapClient;

    @Override
    public void execute(DelegateExecution execution) {
        String requestId = (String) execution.getVariable("requestId");
        String billingInfo = soapClient.confirmBooking(requestId);
        
        try (FileWriter writer = new FileWriter("posting_requests.txt", true)) {
            writer.write(requestId + ", " + billingInfo + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file", e);
        }
        
        execution.setVariable("billingInfo", billingInfo);
    }
}