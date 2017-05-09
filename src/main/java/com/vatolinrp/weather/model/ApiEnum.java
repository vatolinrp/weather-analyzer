package com.vatolinrp.weather.model;

public enum ApiEnum {

  AW( "AW", "AccuWeather" ),
  DS( "DS", "Dark Sky" );

  private String code;
  private String name;

  ApiEnum( String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
}
