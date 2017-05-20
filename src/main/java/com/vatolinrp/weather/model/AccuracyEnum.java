package com.vatolinrp.weather.model;

public enum AccuracyEnum {
  ACCURATE_TEMPERATURE( .5, 1. ),
  ACCURATE_WIND_SPEED( 1. ),
  CLOSE_TO_ACCURATE_TEMPERATURE( 1., 2. ),
  CLOSE_TO_ACCURATE_WIND_SPEED( 2. ),
  NOT_ACCURATE_WIND_SPEED( 10. ),
  NOT_ACCURATE_TEMPERATURE( 10., 10. );

  AccuracyEnum( Double rangeInCelsius, Double rangeInFahrenheit ) {
    this.rangeInCelsius = rangeInCelsius;
    this.rangeInFahrenheit = rangeInFahrenheit;
  }

  AccuracyEnum( Double rangeInMilesPerHour ) {
    this.rangeInMilesPerHour = rangeInMilesPerHour;
  }

  private Double rangeInCelsius;
  private Double rangeInFahrenheit;
  private Double rangeInMilesPerHour;

  public static AccuracyEnum getAccuracyByFahrenheitDiff( Double diff ) {
    if( diff <= ACCURATE_TEMPERATURE.getRangeInFahrenheit() ){
      return ACCURATE_TEMPERATURE;
    }
    if( diff > ACCURATE_TEMPERATURE.getRangeInFahrenheit() && diff <= CLOSE_TO_ACCURATE_TEMPERATURE.getRangeInFahrenheit() ) {
      return CLOSE_TO_ACCURATE_TEMPERATURE;
    }
    return NOT_ACCURATE_TEMPERATURE;
  }

  public static AccuracyEnum getAccuracyByMilesPerHourDiff( Double diff ) {
    if( diff <= ACCURATE_WIND_SPEED.getRangeInMilesPerHour() ){
      return ACCURATE_WIND_SPEED;
    }
    if( diff > ACCURATE_WIND_SPEED.getRangeInMilesPerHour() && diff <= CLOSE_TO_ACCURATE_WIND_SPEED.getRangeInMilesPerHour() ) {
      return CLOSE_TO_ACCURATE_WIND_SPEED;
    }
    return NOT_ACCURATE_WIND_SPEED;
  }

  public static AccuracyEnum getAccuracyByCelsiusDiff( Double diff ) {
    if( diff <= ACCURATE_TEMPERATURE.getRangeInCelsius() ){
      return ACCURATE_TEMPERATURE;
    }
    if( diff > ACCURATE_TEMPERATURE.getRangeInCelsius() && diff <= CLOSE_TO_ACCURATE_TEMPERATURE.getRangeInCelsius() ) {
      return CLOSE_TO_ACCURATE_TEMPERATURE;
    }
    return NOT_ACCURATE_TEMPERATURE;
  }

  public Double getRangeInCelsius() {
    return rangeInCelsius;
  }

  public Double getRangeInFahrenheit() {
    return rangeInFahrenheit;
  }

  public Double getRangeInMilesPerHour() {
    return rangeInMilesPerHour;
  }
}
