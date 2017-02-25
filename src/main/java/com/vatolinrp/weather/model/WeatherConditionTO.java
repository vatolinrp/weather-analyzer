package com.vatolinrp.weather.model;
import lombok.Data;

import java.time.ZonedDateTime;

public @Data class WeatherConditionTO {
  private String locationKey;
  private Double temperature;
  private ZonedDateTime targetDate;
  private String transferKey;
}
