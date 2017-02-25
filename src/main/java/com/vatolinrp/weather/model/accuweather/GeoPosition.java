
package com.vatolinrp.weather.model.accuweather;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vatolinrp.weather.model.accuweather.Elevation;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Latitude",
    "Longitude",
    "Elevation"
})
public class GeoPosition {

    @JsonProperty("Latitude")
    private Double latitude;
    @JsonProperty("Longitude")
    private Double longitude;
    @JsonProperty("Elevation")
    private Elevation elevation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("Latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("Longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("Longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("Elevation")
    public Elevation getElevation() {
        return elevation;
    }

    @JsonProperty("Elevation")
    public void setElevation(Elevation elevation) {
        this.elevation = elevation;
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
