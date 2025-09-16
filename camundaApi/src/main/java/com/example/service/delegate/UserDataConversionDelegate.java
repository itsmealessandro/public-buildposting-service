package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.example.model.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserDataConversionDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Recupera JSON dalla variabile di processo
        String json = (String) execution.getVariable("user_info");

        // Conversione JSON in oggetto UserData
        ObjectMapper mapper = new ObjectMapper();
        UserData userData = mapper.readValue(json, UserData.class);

        // Salvataggio come variabile di processo
        execution.setVariable("userDataObj", userData);

        // Debug log
        System.out.println("=== USER DATA OBJECT ===");
        System.out.println(userData);
    }
}
