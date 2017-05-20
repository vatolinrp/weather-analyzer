package com.vatolinrp.weather.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

public @Data class HourAccuracy implements Serializable {
  private Integer hour;
  private LocalDate date;
  private ApiEnum apiType;
  private String locationKey;
  private Double expectedTemperature;
  private Double actualTemperature;
  private Double expectedWindSpeed;
  private Double actualWindSpeed;
}
