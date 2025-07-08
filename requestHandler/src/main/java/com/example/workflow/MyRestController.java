package com.example.workflow;
// Importa le classi necessarie per Spring e Camunda
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Questa classe è un controller REST che gestisce le richieste HTTP
 * per avviare processi BPMN tramite messaggi Camunda.
 */
@RestController // Annota la classe come controller REST
public class MyRestController {

    // Inietta il RuntimeService di Camunda per interagire con il motore dei processi
    @Autowired
    private RuntimeService runtimeService;

    /**
     * Questo metodo gestisce le richieste GET all'endpoint "/message-1".
     * Quando viene richiamato, correla il messaggio "message-1" con un processo BPMN
     * e inietta una variabile al suo avvio.
     */
    @GetMapping("message-1") // Mappa questo metodo alle richieste GET su "/message-1"
    public void getMessageOne() {
        // Crea una correlazione per il messaggio denominato "message-1"
        runtimeService
            .createMessageCorrelation("message-1")
            // Inietta una variabile chiamata "InputVariable" con un valore specifico
            // Questa variabile può essere utilizzata all'interno del processo BPMN
            .setVariable("InputVariable", "Valore Variabile di Input per il saluto")
            // Esegue la correlazione, che avvierà un'istanza del processo BPMN
            // che ha un "Message Start Event" in attesa di "message-1"
            .correlate();
        System.out.println("Messaggio 'message-1' correlato. Processo BPMN avviato.");
        // Puoi aggiungere qui una logica per rispondere alla richiesta HTTP,
        // ad esempio, inviando un messaggio di successo.
    }
}
