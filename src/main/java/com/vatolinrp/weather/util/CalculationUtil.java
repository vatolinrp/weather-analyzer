package com.vatolinrp.weather.util;

public class CalculationUtil {

  public static Double getCelsius( Double fahrenheit ) {
    return (fahrenheit - 32.)/1.8;
  }

  public static Double getKms( Double miles ) {
    return miles * 1.60934;
  }

  public static Double round( Double value ) {
    return (double) Math.round(value * 100) / 100;
  }
}
