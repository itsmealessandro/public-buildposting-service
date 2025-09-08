package com.example.service.delegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.example.model.Zone;

@Component
public class SelectZonesDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) {
    System.out.println("SELCTING ZONES...");
    System.out.println();
    List<String> cities = (List<String>) execution.getVariable("cities");
    Map<String, Double> maxPrices = (Map<String, Double>) execution.getVariable("maxPrices");
    List<Zone> allZones = (List<Zone>) execution.getVariable("zones");

    Map<String, List<Zone>> selectedZones = new HashMap<>();
    double totalPrice = 0.0;

    for (String city : cities) {
      List<Zone> cityZones = new ArrayList<>();
      for (Zone zone : allZones) {
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
