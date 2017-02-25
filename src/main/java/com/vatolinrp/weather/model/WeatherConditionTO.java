package com.vatolinrp.weather.model;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

public @Data class WeatherConditionTO implements Serializable {
  private String locationKey;
  private Double temperature;
  private ZonedDateTime targetDate;
  private String transferKey;
}
