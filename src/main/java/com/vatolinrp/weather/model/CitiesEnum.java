package com.vatolinrp.weather.model;

public enum CitiesEnum
{
  MINSK( "28580", "53.9", "27.576", "Europe/Minsk" ),

  BREST( "27497","52.096","23.703","Europe/Minsk" ),

  PINSK( "27500", "52.12", "26.104", "Europe/Minsk" ),

  MOZYR( "28575", "52.046", "29.268", "Europe/Minsk" ),

  GOMEL( "28573", "52.444", "30.98", "Europe/Minsk" ),

  MOGILEV( "29675", "53.9", "30.35", "Europe/Minsk" ),

  VITEBSK( "31474", "55.19", "30.211", "Europe/Minsk" ),

  POLATSK( "31230", "55.483", "28.783", "Europe/Minsk" ),

  SMORGON( "1-28582_1_AL", "54.483", "26.416", "Europe/Minsk" ),

  GRODNO( "28800", "53.683", "23.833", "Europe/Minsk" );

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
