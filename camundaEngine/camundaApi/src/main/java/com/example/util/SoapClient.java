package com.example.util;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.xml.transform.StringSource;

import com.example.model.Zone;

@Component
public class SoapClient extends WebServiceGatewaySupport {

    public String callPostingService(Object user, Map<String, List<Zone>> selectedZones, String format) {
        try {
            String soapRequest = buildSoapRequest(user, selectedZones, format);
            String response = sendSoapRequest(soapRequest);
            return extractRequestId(response);
        } catch (Exception e) {
            throw new RuntimeException("SOAP call failed", e);
        }
    }
    
    public String confirmBooking(String requestId) {
        try {
            String soapRequest = buildConfirmationSoapRequest(requestId);
            String response = sendSoapRequest(soapRequest);
            return extractBillingInfo(response);
        } catch (Exception e) {
            throw new RuntimeException("SOAP confirmation failed", e);
        }
    }

    private String buildSoapRequest(Object user, Map<String, List<Zone>> selectedZones, String format) {
        StringBuilder zonesXml = new StringBuilder();
        for (List<Zone> cityZones : selectedZones.values()) {
            for (Zone zone : cityZones) {
                zonesXml.append("<pos:zone>")
                        .append("<pos:id>").append(zone.getId()).append("</pos:id>")
                        .append("<pos:city>").append(zone.getCity()).append("</pos:city>")
                        .append("</pos:zone>");
            }
        }

        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
               "xmlns:pos=\"http://dissimator.com/content/2024-03-01\">" +
               "<soapenv:Header/>" +
               "<soapenv:Body>" +
               "<pos:availabilityRequest>" +
               "<pos:applicant>" +
               "<pos:name>MockName</pos:name>" + // Usa dati reali
               "</pos:applicant>" +
               "<pos:postage>" +
               "<pos:posterFormat>" + format + "</pos:posterFormat>" +
               zonesXml.toString() +
               "</pos:postage>" +
               "</pos:availabilityRequest>" +
               "</soapenv:Body>" +
               "</soapenv:Envelope>";
    }
    
    private String buildConfirmationSoapRequest(String requestId) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
               "xmlns:pos=\"http://dissimator.com/content/2024-03-01\">" +
               "<soapenv:Header/>" +
               "<soapenv:Body>" +
               "<pos:confirmationRequest>" +
               "<pos:requestId>" + requestId + "</pos:requestId>" +
               "</pos:confirmationRequest>" +
               "</soapenv:Body>" +
               "</soapenv:Envelope>";
    }

    private String sendSoapRequest(String soapRequest) {
        WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
        StringWriter writer = new StringWriter();
        
        webServiceTemplate.sendSourceAndReceiveToResult(
            "http://localhost:8888/postingservice", 
            new StringSource(soapRequest),
            new StreamResult(writer)
        );
        
        return writer.toString();
    }

    private String extractRequestId(String soapResponse) {
        if (soapResponse.contains("<ns2:requestId>")) {
            int start = soapResponse.indexOf("<ns2:requestId>") + 15;
            int end = soapResponse.indexOf("</ns2:requestId>", start);
            return soapResponse.substring(start, end);
        }
        return "MOCK-REQ-ID";
    }
    
    private String extractBillingInfo(String soapResponse) {
        if (soapResponse.contains("<ns2:amountDue>")) {
            int start = soapResponse.indexOf("<ns2:amountDue>") + 15;
            int end = soapResponse.indexOf("</ns2:amountDue>", start);
            String amount = soapResponse.substring(start, end);
            return "Amount due: " + amount;
        }
        return "Billing info not available";
    }

    public void cancelBooking(String requestId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelBooking'");
    }
}