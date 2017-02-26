package com.vatolinrp.weather;

public enum CitiesEnum {
  MINSK( "28580", "53.9", "27.576", "Europe/Minsk" ),
  FANIPAL( "29711", "53.758", "27.335", "Europe/Minsk" ),
  MIKHANOVICHI( "29722", "53.74", "27.684", "Europe/Minsk" );

  private String cityId;
  private String latitude;
  private String longitude;
  private String timeZone;

  CitiesEnum( String cityId, String latitude, String longitude, String timeZone ) {
    this.cityId = cityId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.timeZone = timeZone;
  }

  public static CitiesEnum getNameByCityId( String cityId ) {
    for( CitiesEnum city: CitiesEnum.values() ) {
      if(city.getCityId().equals( cityId ) ) {
        return city;
      }
    }
    return null;
  }
  public String getCityId() {
    return cityId;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public String getLatitude() {
    return latitude;
  }

  public String getLongitude() {
    return longitude;
  }
}
