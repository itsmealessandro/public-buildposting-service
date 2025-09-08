package com.example.service.delegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    String zonesJson = (String) execution.getVariable("zones");
    System.out.println("zonesObj:" + zonesJson);

    ObjectMapper mapper = new ObjectMapper();
    List<Zone> zones = null;
    try {
      zones = mapper.readValue(zonesJson, new TypeReference<List<Zone>>() {
      });
    } catch (JsonProcessingException e) {
      System.out.println("error JSON");
      e.printStackTrace();
    }

    System.out.println("ZONES:");
    zones.forEach(System.out::println);

    List<String> cities = (List<String>) execution.getVariable("client_cities");
    System.out.println("CITIES:");
    System.out.println(cities);

    System.out.println("ZONES:");
    System.out.println(zones);
    System.out.println("CITIES:");
    System.out.println(cities);
    Map<String, Double> maxPrices = (Map<String, Double>) execution.getVariable("maxPrices");

    Map<String, List<Zone>> selectedZones = new HashMap<>();
    double totalPrice = 0.0;

    for (String city : cities) {
      List<Zone> cityZones = new ArrayList<>();
      for (Zone zone : zones) {
        if (zone.getCity().equals(city)) {
          cityZones.add(zone);
        }
      }

      // Correzione: usa Comparator esplicito
      cityZones.sort((z1, z2) -> Double.compare(z2.getPrice(), z1.getPrice()));

      List<Zone> selected = new ArrayList<>();
      double currentTotal = 0.0;
      for (Zone zone : cityZones) {
        if (currentTotal + zone.getPrice() <= maxPrices.get(city)) {
          selected.add(zone);
          currentTotal += zone.getPrice();
        } else {
          break;
        }
      }

      selectedZones.put(city, selected);
      totalPrice += currentTotal;
    }

    execution.setVariable("selectedZones", selectedZones);
    execution.setVariable("totalPrice", totalPrice);
  }
}
