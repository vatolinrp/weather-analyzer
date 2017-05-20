
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
    "Speed",
    "Direction"
})
public class CurrentWind {

  @JsonProperty("Speed")
  private ComplexSpeed speed;
  @JsonProperty("Direction")
  private Direction direction;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("Speed")
  public ComplexSpeed getSpeed() {
    return speed;
  }

  @JsonProperty("Speed")
  public void setSpeed(ComplexSpeed speed) {
    this.speed = speed;
  }

  @JsonProperty("Direction")
  public Direction getDirection() {
    return direction;
  }

  @JsonProperty("Direction")
  public void setDirection(Direction direction) {
    this.direction = direction;
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
