package com.example.service.delegate;

import java.util.Arrays;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.model.Zone;

@Component
public class GetZonesDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        String format = (String) execution.getVariable("format");
        String url = "http://localhost:9090/zones/" + format;
        
        RestTemplate restTemplate = new RestTemplate();
        Zone[] zonesArray = restTemplate.getForObject(url, Zone[].class);
        List<Zone> allZones = Arrays.asList(zonesArray);
        
        execution.setVariable("allZones", allZones);
    }
}