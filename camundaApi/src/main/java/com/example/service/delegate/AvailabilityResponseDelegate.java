package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.xml.SpinXmlElement;

public class AvailabilityResponseDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String response = (String) execution.getVariable("availabilityResponse");
        if (response != null) {
            response = response.trim(); // pulizia
            // Parsing XML con Spin
            SpinXmlElement  xml = Spin.XML(response);
            String available = xml.childElement("Body")
                .childElement("http://disim.univaq.it/services/postingservice", "availabilityResponse")
                .childElement("available")
                .textContent();
            execution.setVariable("available", available);
        }
    }
}