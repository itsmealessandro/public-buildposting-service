package com.example.model;

import java.util.List;
import java.util.Map;

public class CityZones {

  private Map<String, List<Zone>> cityZones;

  public CityZones() {
  }

  public Map<String, List<Zone>> getCityZones() {
    return cityZones;
  }

  public void setCityZones(Map<String, List<Zone>> cityZones) {
    this.cityZones = cityZones;
  }

}
