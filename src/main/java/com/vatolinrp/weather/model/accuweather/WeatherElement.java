
package com.vatolinrp.weather.model.accuweather;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "LocalObservationDateTime",
        "EpochTime",
        "WeatherText",
        "WeatherIcon",
        "IsDayTime",
        "Temperature",
        "Wind",
        "MobileLink",
        "Link"
})
public class WeatherElement {

  @JsonProperty("LocalObservationDateTime")
  private String localObservationDateTime;
  @JsonProperty("EpochTime")
  private Integer epochTime;
  @JsonProperty("WeatherText")
  private String weatherText;
  @JsonProperty("WeatherIcon")
  private Integer weatherIcon;
  @JsonProperty("IsDayTime")
  private Boolean isDayTime;
  @JsonProperty("Temperature")
  private Temperature temperature;
  @JsonProperty("Wind")
  private CurrentWind wind;
  @JsonProperty("MobileLink")
  private String mobileLink;
  @JsonProperty("Link")
  private String link;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The localObservationDateTime
   */
  @JsonProperty("LocalObservationDateTime")
  public String getLocalObservationDateTime() {
    return localObservationDateTime;
  }

  /**
   * @param localObservationDateTime The LocalObservationDateTime
   */
  @JsonProperty("LocalObservationDateTime")
  public void setLocalObservationDateTime(String localObservationDateTime) {
    this.localObservationDateTime = localObservationDateTime;
  }

  /**
   * @return The epochTime
   */
  @JsonProperty("EpochTime")
  public Integer getEpochTime() {
    return epochTime;
  }

  /**
   * @param epochTime The EpochTime
   */
  @JsonProperty("EpochTime")
  public void setEpochTime(Integer epochTime) {
    this.epochTime = epochTime;
  }

  /**
   * @return The weatherText
   */
  @JsonProperty("WeatherText")
  public String getWeatherText() {
    return weatherText;
  }

  /**
   * @param weatherText The WeatherText
   */
  @JsonProperty("WeatherText")
  public void setWeatherText(String weatherText) {
    this.weatherText = weatherText;
  }

  /**
   * @return The weatherIcon
   */
  @JsonProperty("WeatherIcon")
  public Integer getWeatherIcon() {
    return weatherIcon;
  }

  /**
   * @param weatherIcon The WeatherIcon
   */
  @JsonProperty("WeatherIcon")
  public void setWeatherIcon(Integer weatherIcon) {
    this.weatherIcon = weatherIcon;
  }

  /**
   * @return The isDayTime
   */
  @JsonProperty("IsDayTime")
  public Boolean getIsDayTime() {
    return isDayTime;
  }

  /**
   * @param isDayTime The IsDayTime
   */
  @JsonProperty("IsDayTime")
  public void setIsDayTime(Boolean isDayTime) {
    this.isDayTime = isDayTime;
  }

  /**
   * @return The temperature
   */
  @JsonProperty("Temperature")
  public Temperature getTemperature() {
    return temperature;
  }

  /**
   * @param temperature The Temperature
   */
  @JsonProperty("Temperature")
  public void setTemperature(Temperature temperature) {
    this.temperature = temperature;
  }

  /**
   * @return The wind
   */
  @JsonProperty("Wind")
  public CurrentWind getWind() {
    return wind;
  }

  /**
   * @param wind The Wind
   */
  @JsonProperty("Wind")
  public void setWind(CurrentWind wind) {
    this.wind = wind;
  }

  /**
   * @return The mobileLink
   */
  @JsonProperty("MobileLink")
  public String getMobileLink() {
    return mobileLink;
  }

  /**
   * @param mobileLink The MobileLink
   */
  @JsonProperty("MobileLink")
  public void setMobileLink(String mobileLink) {
    this.mobileLink = mobileLink;
  }

  /**
   * @return The link
   */
  @JsonProperty("Link")
  public String getLink() {
    return link;
  }

  /**
   * @param link The Link
   */
  @JsonProperty("Link")
  public void setLink(String link) {
    this.link = link;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
