package com.example.model;

public class DecisionResponse {
    private String message;
    private String invoiceNumber;
    private Double amountDue;
    private String status;

    public DecisionResponse(String message) {
        this.message = message;
    }

    public DecisionResponse(String message, String invoiceNumber, Double amountDue, String status) {
        this.message = message;
        this.invoiceNumber = invoiceNumber;
        this.amountDue = amountDue;
        this.status = status;
    }

    public DecisionResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}