package com.vatolinrp.weather;

public enum AccuracyEnum {
  ACCURATE( .5, 1. ),
  CLOSE_TO_ACCURATE( 1., 2. ),
  NOT_ACCURATE( 2., 4. );

  AccuracyEnum( Double rangeInCelsius, Double rangeInFahrenheit ) {
    this.rangeInCelsius = rangeInCelsius;
    this.rangeInFahrenheit = rangeInFahrenheit;
  }

  private Double rangeInCelsius;
  private Double rangeInFahrenheit;

  public static AccuracyEnum getAccuracyByFahrenheitDiff( Double diff ) {
    if( diff <= ACCURATE.getRangeInFahrenheit() ){
      return ACCURATE;
    }
    if( diff > ACCURATE.getRangeInFahrenheit() && diff <= CLOSE_TO_ACCURATE.getRangeInFahrenheit() ) {
      return CLOSE_TO_ACCURATE;
    }
    return NOT_ACCURATE;
  }

  public static AccuracyEnum getAccuracyByCelsiusDiff( Double diff ) {
    if( diff <= ACCURATE.getRangeInCelsius() ){
      return ACCURATE;
    }
    if( diff > ACCURATE.getRangeInCelsius() && diff <= CLOSE_TO_ACCURATE.getRangeInCelsius() ) {
      return CLOSE_TO_ACCURATE;
    }
    return NOT_ACCURATE;
  }

  public Double getRangeInCelsius() {
    return rangeInCelsius;
  }

  public Double getRangeInFahrenheit() {
    return rangeInFahrenheit;
  }
}
