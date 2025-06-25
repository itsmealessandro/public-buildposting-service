package com.example.model;

public class DecisionResponse {
    private String message;
    
    // Aggiungi il costruttore con parametro String
    public DecisionResponse(String message) {
        this.message = message;
    }
    
    // Aggiungi il costruttore vuoto (necessario per la deserializzazione JSON)
    public DecisionResponse() {
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}