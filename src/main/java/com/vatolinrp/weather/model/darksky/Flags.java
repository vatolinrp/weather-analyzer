
package com.vatolinrp.weather.model.darksky;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sources",
    "isd-stations",
    "madis-stations",
    "units"
})
public class Flags {

    @JsonProperty("sources")
    private List<String> sources = null;
    @JsonProperty("isd-stations")
    private List<String> isdStations = null;
    @JsonProperty("madis-stations")
    private List<String> madisStations = null;
    @JsonProperty("units")
    private String units;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sources")
    public List<String> getSources() {
        return sources;
    }

    @JsonProperty("sources")
    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    @JsonProperty("isd-stations")
    public List<String> getIsdStations() {
        return isdStations;
    }

    @JsonProperty("isd-stations")
    public void setIsdStations(List<String> isdStations) {
        this.isdStations = isdStations;
    }

    @JsonProperty("madis-stations")
    public List<String> getMadisStations() {
        return madisStations;
    }

    @JsonProperty("madis-stations")
    public void setMadisStations(List<String> madisStations) {
        this.madisStations = madisStations;
    }

    @JsonProperty("units")
    public String getUnits() {
        return units;
    }

    @JsonProperty("units")
    public void setUnits(String units) {
        this.units = units;
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
