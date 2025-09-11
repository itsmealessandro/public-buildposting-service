package com.example.service.delegate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.example.model.Zone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SelectZonesDelegate implements JavaDelegate {

  @SuppressWarnings("unchecked")
  @Override
  public void execute(DelegateExecution execution) {

    System.out.println("SELECTING ZONES...");

    // Recupero delle variabili da Camunda
    String zonesJson = (String) execution.getVariable("zones");
    String algorithm = (String) execution.getVariable("algorithm"); // <-- scelta algoritmo
    List<String> cities = (List<String>) execution.getVariable("client_cities");
    Map<String, Double> maxPrices = (Map<String, Double>) execution.getVariable("maxPrices");

    ObjectMapper mapper = new ObjectMapper();
    List<Zone> zones = null;
    try {
      zones = mapper.readValue(zonesJson, new TypeReference<List<Zone>>() {
      });
    } catch (JsonProcessingException e) {
      System.err.println("Error parsing zones JSON");
      e.printStackTrace();
      return;
    }

    System.out.println("ZONES:");
    zones.forEach(System.out::println);
    System.out.println("CITIES: " + cities);
    System.out.println("ALGORITHM: " + algorithm);

    Map<String, List<Zone>> selectedZones;
    double totalPrice;

    // Selezione algoritmo
    if ("eco".equalsIgnoreCase(algorithm)) {
      selectedZones = selectZonesReverse(zones, cities, maxPrices);
    } else {
      selectedZones = selectZonesGreedy(zones, cities, maxPrices);
    }

    totalPrice = selectedZones.values().stream()
        .flatMap(List::stream)
        .mapToDouble(Zone::getPrice)
        .sum();

    execution.setVariable("selectedZones", selectedZones);
    execution.setVariable("totalPrice", totalPrice);
  }

  /**
   * Seleziona le zone usando un approccio greedy:
   * prendi quelle più costose finché non raggiungi il budget.
   */
  private Map<String, List<Zone>> selectZonesGreedy(List<Zone> zones, List<String> cities,
      Map<String, Double> maxPrices) {
    Map<String, List<Zone>> result = new HashMap<>();

    for (String city : cities) {
      List<Zone> cityZones = zones.stream()
          .filter(z -> z.getCity().equals(city))
          .sorted((z1, z2) -> Double.compare(z2.getPrice(), z1.getPrice())) // ordina discendente
          .collect(Collectors.toList()); // <-- CORRETTO per Java 8/11

      List<Zone> selected = new ArrayList<>();
      double currentTotal = 0.0;

      for (Zone zone : cityZones) {
        if (currentTotal + zone.getPrice() <= maxPrices.get(city)) {
          selected.add(zone);
          currentTotal += zone.getPrice();
        }
      }
      result.put(city, selected);
    }
    System.out.println("selected List:" + result);
    return result;
  }

  /**
   * Seleziona le zone usando l'approccio inverso:
   * prendi quelle più economiche finché non raggiungi il budget.
   */
  private Map<String, List<Zone>> selectZonesReverse(List<Zone> zones, List<String> cities,
      Map<String, Double> maxPrices) {
    Map<String, List<Zone>> result = new HashMap<>();

    for (String city : cities) {
      List<Zone> cityZones = zones.stream()
          .filter(z -> z.getCity().equals(city))
          .sorted(Comparator.comparingDouble(Zone::getPrice)) // ordina crescente
          .collect(Collectors.toList()); // <-- CORRETTO per Java 8/11

      List<Zone> selected = new ArrayList<>();
      double currentTotal = 0.0;

      for (Zone zone : cityZones) {
        if (currentTotal + zone.getPrice() <= maxPrices.get(city)) {
          selected.add(zone);
          currentTotal += zone.getPrice();
        }
      }
      result.put(city, selected);
    }
    System.out.println("selected List:" + result);
    return result;
  }
}
