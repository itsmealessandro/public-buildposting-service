package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.xml.SpinXmlElement;

public class FirstResponseDelegate implements JavaDelegate {
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

                // Naviga nella struttura SOAP per arrivare al contenuto
                SpinXmlElement body = xml.childElement("Body");
                SpinXmlElement availabilityResponse = body
                    .childElement("http://disim.univaq.it/services/postingservice", "availabilityResponse");

                // Estrai i campi desiderati
                String requestId = availabilityResponse.childElement("requestId").textContent();
                String available = availabilityResponse.childElement("available").textContent();

                // Salva le variabili nel contesto Camunda
                execution.setVariable("requestId", requestId);
                execution.setVariable("available", available);

                // Stampa per debug
                System.out.println("Request ID: " + requestId);
                System.out.println("Available: " + available);
            } else {
                System.out.println("La risposta non è XML. Nessun parsing effettuato.");
                execution.setVariable("available", null);
                execution.setVariable("requestId", null);
            }
        }
    }
}
