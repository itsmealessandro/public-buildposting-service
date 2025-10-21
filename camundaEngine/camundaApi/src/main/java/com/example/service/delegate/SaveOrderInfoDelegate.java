package com.example.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveOrderInfoDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {

    // Recupera businessKey (identificatore univoco del processo)
    String businessKey = execution.getProcessBusinessKey();
    if (businessKey == null || businessKey.trim().isEmpty()) {
      businessKey = String.valueOf(System.currentTimeMillis());
    }

    // === Estrai informazioni utente ===
    String userInfo = getString(execution, "user_info");

    String username = extractValue(userInfo, "username");
    String name = extractValue(userInfo, "name");
    String surname = extractValue(userInfo, "surname");
    String taxCode = extractValue(userInfo, "taxCode");
    String address = extractValue(userInfo, "address");
    String city = extractValue(userInfo, "city");
    String zipCode = extractValue(userInfo, "zipCode");
    String phone = extractValue(userInfo, "phone");
    String email = extractValue(userInfo, "email");

    // === Estrai informazioni ordine ===
    String requestId = getString(execution, "requestId");
    String invoiceNumber = getString(execution, "invoiceNumber");
    String amountDue = getString(execution, "amountDue");
    String totalPrice = getString(execution, "totalPrice");
    String format = getString(execution, "format");
    String selectedZones = getString(execution, "selectedZones");

    // === Crea nome file ===
    String safeName = (name != null && !name.isEmpty()) ? name.replaceAll("\\s+", "_") : "Unknown";
    String safeSurname = (surname != null && !surname.isEmpty()) ? surname.replaceAll("\\s+", "_") : "Unknown";
    final String PATH_DIR = "/persistent_data";
    String fileName = safeName + "_" + safeSurname + "_" + businessKey + ".txt";

    File file = new File(PATH_DIR + "/" + fileName);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("=== DATI CLIENTE ===\n");
      writer.write("Nome: " + name + "\n");
      writer.write("Cognome: " + surname + "\n");
      writer.write("Username: " + username + "\n");
      writer.write("Codice Fiscale: " + taxCode + "\n");
      writer.write("Email: " + email + "\n");
      writer.write("Telefono: " + phone + "\n");
      writer.write("Indirizzo: " + address + "\n");
      writer.write("Città: " + city + "\n");
      writer.write("CAP: " + zipCode + "\n");

      writer.write("\n=== DETTAGLI ORDINE ===\n");
      writer.write("Business Key: " + businessKey + "\n");
      writer.write("Request ID: " + requestId + "\n");
      writer.write("Numero Fattura: " + invoiceNumber + "\n");
      writer.write("Formato Poster: " + format + "\n");
      writer.write("Importo Dovuto: " + amountDue + "\n");
      writer.write("Totale Calcolato: " + totalPrice + "\n");

      writer.write("\nZone selezionate:\n" + selectedZones + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("✅ File ordine salvato: " + file.getAbsolutePath());
  }

  // --- Utility Methods ---

  private String getString(DelegateExecution execution, String varName) {
    Object value = execution.getVariable(varName);
    return value != null ? value.toString() : "";
  }

  /**
   * Estrae un valore dal formato:
   * UserData{username='...', name='...', ...}
   */
  /**
   * Estrae un valore dal formato:
   * UserData{username='...', name='...', ...}
   * Gestisce correttamente gli apici interni come in city='L'Aquila'
   */
  private String extractValue(String userInfo, String fieldName) {
    if (userInfo == null || userInfo.isEmpty())
      return "";
    String key = fieldName + "=";
    int start = userInfo.indexOf(key);
    if (start == -1)
      return "";
    start += key.length();

    // Se inizia con apice singolo
    if (userInfo.charAt(start) == '\'') {
      start++; // salta l'apice iniziale
      StringBuilder value = new StringBuilder();
      boolean escaped = false;
      for (int i = start; i < userInfo.length(); i++) {
        char c = userInfo.charAt(i);
        if (c == '\'' && !escaped) {
          // controlla se è una chiusura o un apice interno
          if (i + 1 < userInfo.length() && userInfo.charAt(i + 1) != ',' && userInfo.charAt(i + 1) != '}') {
            // apice interno come in L'Aquila → aggiungilo al valore
            value.append(c);
            continue;
          } else {
            break; // apice di chiusura vero
          }
        }
        value.append(c);
      }
      return value.toString().trim();
    } else {
      // caso numerico o senza apici
      int end = userInfo.indexOf(",", start);
      if (end == -1)
        end = userInfo.indexOf("}", start);
      if (end > start)
        return userInfo.substring(start, end).trim();
    }
    return "";
  }
}
