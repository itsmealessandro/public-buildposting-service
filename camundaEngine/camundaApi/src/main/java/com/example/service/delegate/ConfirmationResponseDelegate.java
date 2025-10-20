package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.xml.SpinXmlElement;

public class ConfirmationResponseDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    String response = (String) execution.getVariable("confirmResponse");
    System.out.println("Response: " + response);

    if (response != null && !response.trim().isEmpty()) {
      response = response.trim();

      // Parsing XML con Spin
      if (response.startsWith("<")) {
        SpinXmlElement xml = Spin.XML(response);
        System.out.println("spin result: " + xml);

        // Naviga nella struttura SOAP
        SpinXmlElement body = xml.childElement("Body");
        SpinXmlElement confirmationResponse = body
            .childElement("http://disim.univaq.it/services/postingservice", "confirmationResponse");

        if (confirmationResponse != null) {
          SpinXmlElement bill = confirmationResponse
              .childElement("http://disim.univaq.it/services/postingservice", "bill");

          if (bill != null) {
            String accountHolder = bill.childElement("http://disim.univaq.it/services/postingservice", "accountHolder")
                .textContent();
            String invoiceNumber = bill.childElement("http://disim.univaq.it/services/postingservice", "invoiceNumber")
                .textContent();
            String amountDue = bill.childElement("http://disim.univaq.it/services/postingservice", "amountDue")
                .textContent();

            // Salva nel contesto Camunda
            execution.setVariable("accountHolder", accountHolder);
            execution.setVariable("invoiceNumber", invoiceNumber);
            execution.setVariable("amountDue", amountDue);

            System.out.println("###############################");
            System.out.println("accountHolder: " + accountHolder);
            System.out.println("invoiceNumber: " + invoiceNumber);
            System.out.println("amountDue: " + amountDue);
          }
        }
      } else {
        System.out.println("La risposta non è XML. Nessun parsing effettuato.");
        execution.setVariable("accountHolder", null);
        execution.setVariable("invoiceNumber", null);
        execution.setVariable("amountDue", null);
      }
    } else {
      System.out.println("La risposta è vuota o nulla.");
      execution.setVariable("accountHolder", null);
      execution.setVariable("invoiceNumber", null);
      execution.setVariable("amountDue", null);
    }
  }
}
