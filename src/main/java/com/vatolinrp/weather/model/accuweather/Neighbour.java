
package com.vatolinrp.weather.model.accuweather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vatolinrp.weather.model.accuweather.AdministrativeArea;
import com.vatolinrp.weather.model.accuweather.Country;
import com.vatolinrp.weather.model.accuweather.Details;
import com.vatolinrp.weather.model.accuweather.GeoPosition;
import com.vatolinrp.weather.model.accuweather.Region;
import com.vatolinrp.weather.model.accuweather.TimeZone;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Version",
    "Key",
    "Type",
    "Rank",
    "LocalizedName",
    "EnglishName",
    "PrimaryPostalCode",
    "Region",
    "Country",
    "AdministrativeArea",
    "TimeZone",
    "GeoPosition",
    "IsAlias",
    "SupplementalAdminAreas",
    "DataSets",
    "Details"
})
public class Neighbour {

    @JsonProperty("Version")
    private Integer version;
    @JsonProperty("Key")
    private String key;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Rank")
    private Integer rank;
    @JsonProperty("LocalizedName")
    private String localizedName;
    @JsonProperty("EnglishName")
    private String englishName;
    @JsonProperty("PrimaryPostalCode")
    private String primaryPostalCode;
    @JsonProperty("Region")
    private Region region;
    @JsonProperty("Country")
    private Country country;
    @JsonProperty("AdministrativeArea")
    private AdministrativeArea administrativeArea;
    @JsonProperty("TimeZone")
    private TimeZone timeZone;
    @JsonProperty("GeoPosition")
    private GeoPosition geoPosition;
    @JsonProperty("IsAlias")
    private Boolean isAlias;
    @JsonProperty("SupplementalAdminAreas")
    private List<Object> supplementalAdminAreas = null;
    @JsonProperty("DataSets")
    private List<Object> dataSets = null;
    @JsonProperty("Details")
    private Details details;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Version")
    public Integer getVersion() {
        return version;
    }

    @JsonProperty("Version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    @JsonProperty("Key")
    public String getKey() {
        return key;
    }

    @JsonProperty("Key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("Type")
    public String getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("Rank")
    public Integer getRank() {
        return rank;
    }

    @JsonProperty("Rank")
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @JsonProperty("LocalizedName")
    public String getLocalizedName() {
        return localizedName;
    }

    @JsonProperty("LocalizedName")
    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    @JsonProperty("EnglishName")
    public String getEnglishName() {
        return englishName;
    }

    @JsonProperty("EnglishName")
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @JsonProperty("PrimaryPostalCode")
    public String getPrimaryPostalCode() {
        return primaryPostalCode;
    }

    @JsonProperty("PrimaryPostalCode")
    public void setPrimaryPostalCode(String primaryPostalCode) {
        this.primaryPostalCode = primaryPostalCode;
    }

    @JsonProperty("Region")
    public Region getRegion() {
        return region;
    }

    @JsonProperty("Region")
    public void setRegion(Region region) {
        this.region = region;
    }

    @JsonProperty("Country")
    public Country getCountry() {
        return country;
    }

    @JsonProperty("Country")
    public void setCountry(Country country) {
        this.country = country;
    }

    @JsonProperty("AdministrativeArea")
    public AdministrativeArea getAdministrativeArea() {
        return administrativeArea;
    }

    @JsonProperty("AdministrativeArea")
    public void setAdministrativeArea(AdministrativeArea administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    @JsonProperty("TimeZone")
    public TimeZone getTimeZone() {
        return timeZone;
    }

    @JsonProperty("TimeZone")
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @JsonProperty("GeoPosition")
    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    @JsonProperty("GeoPosition")
    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }

    @JsonProperty("IsAlias")
    public Boolean getIsAlias() {
        return isAlias;
    }

    @JsonProperty("IsAlias")
    public void setIsAlias(Boolean isAlias) {
        this.isAlias = isAlias;
    }

    @JsonProperty("SupplementalAdminAreas")
    public List<Object> getSupplementalAdminAreas() {
        return supplementalAdminAreas;
    }

    @JsonProperty("SupplementalAdminAreas")
    public void setSupplementalAdminAreas(List<Object> supplementalAdminAreas) {
        this.supplementalAdminAreas = supplementalAdminAreas;
    }

    @JsonProperty("DataSets")
    public List<Object> getDataSets() {
        return dataSets;
    }

    @JsonProperty("DataSets")
    public void setDataSets(List<Object> dataSets) {
        this.dataSets = dataSets;
    }

    @JsonProperty("Details")
    public Details getDetails() {
        return details;
    }

    @JsonProperty("Details")
    public void setDetails(Details details) {
        this.details = details;
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
