
package com.vatolinrp.weather.model;

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
    "DataType",
    "Source",
    "SourceId"
})
public class Source {

    @JsonProperty("DataType")
    private String dataType;
    @JsonProperty("Source")
    private String source;
    @JsonProperty("SourceId")
    private Integer sourceId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("DataType")
    public String getDataType() {
        return dataType;
    }

    @JsonProperty("DataType")
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @JsonProperty("Source")
    public String getSource() {
        return source;
    }

    @JsonProperty("Source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("SourceId")
    public Integer getSourceId() {
        return sourceId;
    }

    @JsonProperty("SourceId")
    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
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
