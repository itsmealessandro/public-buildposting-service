// GetUserDataDelegate.java
package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GetUserDataDelegate implements JavaDelegate {
    
    @Override
    public void execute(DelegateExecution execution) {
        String username = (String) execution.getVariable("username");
        String url = "http://localhost:9080/user/" + username;
        
        RestTemplate restTemplate = new RestTemplate();
        Object user = restTemplate.getForObject(url, Object.class);
        
        execution.setVariable("user", user);
    }
}