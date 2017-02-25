package com.vatolinrp.weather.model;

import lombok.Data;

public @Data class HourAccuracy {
  private Double percentAccuracy;
  private Double forecast;
  private Double current;
  private Integer hour;
  private String date;
}
