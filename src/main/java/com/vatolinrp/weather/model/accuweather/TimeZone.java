
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
    "Code",
    "Name",
    "GmtOffset",
    "IsDaylightSaving",
    "NextOffsetChange"
})
public class TimeZone {

    @JsonProperty("Code")
    private String code;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("GmtOffset")
    private Double gmtOffset;
    @JsonProperty("IsDaylightSaving")
    private Boolean isDaylightSaving;
    @JsonProperty("NextOffsetChange")
    private Object nextOffsetChange;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Code")
    public String getCode() {
        return code;
    }

    @JsonProperty("Code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("GmtOffset")
    public Double getGmtOffset() {
        return gmtOffset;
    }

    @JsonProperty("GmtOffset")
    public void setGmtOffset(Double gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    @JsonProperty("IsDaylightSaving")
    public Boolean getIsDaylightSaving() {
        return isDaylightSaving;
    }

    @JsonProperty("IsDaylightSaving")
    public void setIsDaylightSaving(Boolean isDaylightSaving) {
        this.isDaylightSaving = isDaylightSaving;
    }

    @JsonProperty("NextOffsetChange")
    public Object getNextOffsetChange() {
        return nextOffsetChange;
    }

    @JsonProperty("NextOffsetChange")
    public void setNextOffsetChange(Object nextOffsetChange) {
        this.nextOffsetChange = nextOffsetChange;
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
