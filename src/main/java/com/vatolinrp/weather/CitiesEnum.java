package com.vatolinrp.weather;

public enum CitiesEnum {
  MINSK( "28580", "53.9", "27.576", "Europe/Minsk" ),
  FANIPAL( "29711", "53.758", "27.335", "Europe/Minsk" ),
  OSTROSHITSKIY_GORODOK( "29726","54.065","27.709", "Europe/Minsk"),
  LESHNITSA( "29802","53.733","27.766", "Europe/Minsk"),
  LUGOVSKAYA_SLOBODA( "29808","53.783","27.85", "Europe/Minsk"),
  NELIDOVICHI( "29819","54.033","27.5", "Europe/Minsk"),
  OZERTSO( "29939","53.833","27.383", "Europe/Minsk"),
  PRILUKI( "29950","53.783","27.417", "Europe/Minsk"),
  SAMOKHVALOVICHI( "29962","53.744","27.493", "Europe/Minsk"),
  MIKHANOVICHI( "29722", "53.74", "27.684", "Europe/Minsk" ),
  BREST("27497","52.096","23.703","Europe/Minsk"),
  CHERNI("27317","52.15","23.75","Europe/Minsk"),
  BŁOTKÓW_DUŻY_TERESPOL("1414279","52.065","23.605","Europe/Warsaw"),
  BŁOTKÓW_MAŁY_TERESPOL("2716180","52.068","23.616","Europe/Warsaw"),
  KOLONIA_MICHALKÓW("2679566","52.041","23.64","Europe/Warsaw"),
  MICHALKÓW("1398182","52.05","23.644","Europe/Warsaw"),
  MIESZCZANY_TERESPOL("2716182","52.078","23.619","Europe/Warsaw"),
  OGRÓDKI_ŁOBACZEWSKIE_TERESPOL("2716184","52.074","23.618","Europe/Warsaw"),
  ROGATKA_TERESPOL("2716187","52.08","23.636","Europe/Warsaw"),
  WŁÓCZKI_TERESPOL("2716190","52.064","23.625","Europe/Warsaw");

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
