package com.vatolinrp.weather.model;

public enum AccuracyEnum {
  ACCURATE_TEMPERATURE( .5, 1. ),
  ACCURATE_PRESSURE( 1 ),
  CLOSE_TO_ACCURATE_TEMPERATURE( 1., 2. ),
  CLOSE_TO_ACCURATE_PRESSURE( 2 ),
  NOT_ACCURATE_PRESSURE( 10 ),
  NOT_ACCURATE_TEMPERATURE( 10., 10. );

  AccuracyEnum( Double rangeInCelsius, Double rangeInFahrenheit ) {
    this.rangeInCelsius = rangeInCelsius;
    this.rangeInFahrenheit = rangeInFahrenheit;
  }

  AccuracyEnum( Integer rangeInBarometric ) {
    this.rangeInBarometric = rangeInBarometric;
  }

  private Double rangeInCelsius;
  private Double rangeInFahrenheit;
  private Integer rangeInBarometric;

  public static AccuracyEnum getAccuracyByFahrenheitDiff( Double diff ) {
    if( diff <= ACCURATE_TEMPERATURE.getRangeInFahrenheit() ){
      return ACCURATE_TEMPERATURE;
    }
    if( diff > ACCURATE_TEMPERATURE.getRangeInFahrenheit() && diff <= CLOSE_TO_ACCURATE_TEMPERATURE.getRangeInFahrenheit() ) {
      return CLOSE_TO_ACCURATE_TEMPERATURE;
    }
    return NOT_ACCURATE_TEMPERATURE;
  }

  public static AccuracyEnum getAccuracyByBarometricDiff( Integer diff ) {
    if( diff <= ACCURATE_PRESSURE.getRangeInBarometric() ){
      return ACCURATE_PRESSURE;
    }
    if( diff > ACCURATE_PRESSURE.getRangeInBarometric() && diff <= CLOSE_TO_ACCURATE_PRESSURE.getRangeInBarometric() ) {
      return CLOSE_TO_ACCURATE_PRESSURE;
    }
    return NOT_ACCURATE_PRESSURE;
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

  public Integer getRangeInBarometric() {
    return rangeInBarometric;
  }
}
