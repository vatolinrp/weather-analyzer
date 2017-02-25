
package com.vatolinrp.weather.model.accuweather;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vatolinrp.weather.model.accuweather.Imperial;
import com.vatolinrp.weather.model.accuweather.Metric;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Metric",
        "Imperial"
})
public class Temperature {

  @JsonProperty("Metric")
  private Metric metric;
  @JsonProperty("Imperial")
  private Imperial imperial;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The metric
   */
  @JsonProperty("Metric")
  public Metric getMetric() {
    return metric;
  }

  /**
   * @param metric The Metric
   */
  @JsonProperty("Metric")
  public void setMetric(Metric metric) {
    this.metric = metric;
  }

  /**
   * @return The imperial
   */
  @JsonProperty("Imperial")
  public Imperial getImperial() {
    return imperial;
  }

  /**
   * @param imperial The Imperial
   */
  @JsonProperty("Imperial")
  public void setImperial(Imperial imperial) {
    this.imperial = imperial;
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
