package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.example.model.UserData;

@Component
public class PrintUserDataDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        UserData userData = (UserData) execution.getVariable("userDataObj");

        if (userData != null) {
            System.out.println("=== USER DATA DETAILS ===");
            System.out.println(userData.toString());
            System.out.println("=========================");
        } else {
            System.out.println("No user data available in process variables.");
        }
    }
}
