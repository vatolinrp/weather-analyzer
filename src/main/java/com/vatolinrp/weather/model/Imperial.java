
package com.vatolinrp.weather.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Value",
        "Unit",
        "UnitType"
})
public class Imperial {

  @JsonProperty("Value")
  private Double value;
  @JsonProperty("Unit")
  private String unit;
  @JsonProperty("UnitType")
  private Integer unitType;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The value
   */
  @JsonProperty("Value")
  public Double getValue() {
    return value;
  }

  /**
   * @param value The Value
   */
  @JsonProperty("Value")
  public void setValue(Double value) {
    this.value = value;
  }

  /**
   * @return The unit
   */
  @JsonProperty("Unit")
  public String getUnit() {
    return unit;
  }

  /**
   * @param unit The Unit
   */
  @JsonProperty("Unit")
  public void setUnit(String unit) {
    this.unit = unit;
  }

  /**
   * @return The unitType
   */
  @JsonProperty("UnitType")
  public Integer getUnitType() {
    return unitType;
  }

  /**
   * @param unitType The UnitType
   */
  @JsonProperty("UnitType")
  public void setUnitType(Integer unitType) {
    this.unitType = unitType;
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
