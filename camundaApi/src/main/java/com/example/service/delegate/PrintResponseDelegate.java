package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.xml.SpinXmlElement;

public class PrintResponseDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String response = (String) execution.getVariable("availabilityResponse");
        System.out.println("Response: " + response);
        if (response != null) {
            response = response.trim();
            // Controlla se la risposta sembra XML
            if (response.startsWith("<")) {
                SpinXmlElement xml = Spin.XML(response);
                System.out.println("spin result: " + xml);
                String available = xml.childElement("Body")
                    .childElement("http://disim.univaq.it/services/postingservice", "availabilityResponse")
                    .childElement("available")
                    .textContent();
                execution.setVariable("available", available);
                System.out.println("Available: " + available);
            } else {
                System.out.println("La risposta non è XML. Nessun parsing effettuato.");
                execution.setVariable("available", null);
            }
        }
    }
}