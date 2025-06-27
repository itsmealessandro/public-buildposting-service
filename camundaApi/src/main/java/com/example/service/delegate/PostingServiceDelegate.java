package com.example.service.delegate;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Zone;
import com.example.util.SoapClient;

@Component
public class PostingServiceDelegate implements JavaDelegate {
    
    @Autowired
    private SoapClient soapClient;
    
    @Override
    public void execute(DelegateExecution execution) {
        Object user = execution.getVariable("user");
        Map<String, List<Zone>> selectedZones = (Map<String, List<Zone>>) execution.getVariable("selectedZones");
        String format = (String) execution.getVariable("format");
        
        String requestId = soapClient.callPostingService(user, selectedZones, format);
        execution.setVariable("requestId", requestId);
    }
}