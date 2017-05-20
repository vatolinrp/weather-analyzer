package com.vatolinrp.weather.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

public @Data class LocationAccuracy implements Serializable{
  private Integer hour;
  private LocalDate date;
  private ApiEnum apiType;
  private String locationKey;
  private Double temperatureDifference;
  private Double windSpeedDifference;
}
