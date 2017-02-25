
package com.vatolinrp.weather.model.accuweather;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vatolinrp.weather.model.accuweather.Speed;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Speed"
})
public class WindGust {

    @JsonProperty("Speed")
    private Speed speed;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Speed")
    public Speed getSpeed() {
        return speed;
    }

    @JsonProperty("Speed")
    public void setSpeed(Speed speed) {
        this.speed = speed;
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
