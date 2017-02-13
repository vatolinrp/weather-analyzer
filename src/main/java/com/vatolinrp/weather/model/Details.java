
package com.vatolinrp.weather.model;

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
    "Key",
    "StationCode",
    "StationGmtOffset",
    "BandMap",
    "Climo",
    "LocalRadar",
    "MediaRegion",
    "Metar",
    "NXMetro",
    "NXState",
    "Population",
    "PrimaryWarningCountyCode",
    "PrimaryWarningZoneCode",
    "Satellite",
    "Synoptic",
    "MarineStation",
    "MarineStationGMTOffset",
    "VideoCode",
    "PartnerID",
    "Sources",
    "CanonicalPostalCode",
    "CanonicalLocationKey"
})
public class Details {

    @JsonProperty("Key")
    private String key;
    @JsonProperty("StationCode")
    private String stationCode;
    @JsonProperty("StationGmtOffset")
    private Double stationGmtOffset;
    @JsonProperty("BandMap")
    private String bandMap;
    @JsonProperty("Climo")
    private String climo;
    @JsonProperty("LocalRadar")
    private String localRadar;
    @JsonProperty("MediaRegion")
    private Object mediaRegion;
    @JsonProperty("Metar")
    private String metar;
    @JsonProperty("NXMetro")
    private String nXMetro;
    @JsonProperty("NXState")
    private String nXState;
    @JsonProperty("Population")
    private Object population;
    @JsonProperty("PrimaryWarningCountyCode")
    private String primaryWarningCountyCode;
    @JsonProperty("PrimaryWarningZoneCode")
    private String primaryWarningZoneCode;
    @JsonProperty("Satellite")
    private String satellite;
    @JsonProperty("Synoptic")
    private String synoptic;
    @JsonProperty("MarineStation")
    private String marineStation;
    @JsonProperty("MarineStationGMTOffset")
    private Object marineStationGMTOffset;
    @JsonProperty("VideoCode")
    private String videoCode;
    @JsonProperty("PartnerID")
    private Object partnerID;
    @JsonProperty("Sources")
    private List<Source> sources = null;
    @JsonProperty("CanonicalPostalCode")
    private String canonicalPostalCode;
    @JsonProperty("CanonicalLocationKey")
    private String canonicalLocationKey;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Key")
    public String getKey() {
        return key;
    }

    @JsonProperty("Key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("StationCode")
    public String getStationCode() {
        return stationCode;
    }

    @JsonProperty("StationCode")
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    @JsonProperty("StationGmtOffset")
    public Double getStationGmtOffset() {
        return stationGmtOffset;
    }

    @JsonProperty("StationGmtOffset")
    public void setStationGmtOffset(Double stationGmtOffset) {
        this.stationGmtOffset = stationGmtOffset;
    }

    @JsonProperty("BandMap")
    public String getBandMap() {
        return bandMap;
    }

    @JsonProperty("BandMap")
    public void setBandMap(String bandMap) {
        this.bandMap = bandMap;
    }

    @JsonProperty("Climo")
    public String getClimo() {
        return climo;
    }

    @JsonProperty("Climo")
    public void setClimo(String climo) {
        this.climo = climo;
    }

    @JsonProperty("LocalRadar")
    public String getLocalRadar() {
        return localRadar;
    }

    @JsonProperty("LocalRadar")
    public void setLocalRadar(String localRadar) {
        this.localRadar = localRadar;
    }

    @JsonProperty("MediaRegion")
    public Object getMediaRegion() {
        return mediaRegion;
    }

    @JsonProperty("MediaRegion")
    public void setMediaRegion(Object mediaRegion) {
        this.mediaRegion = mediaRegion;
    }

    @JsonProperty("Metar")
    public String getMetar() {
        return metar;
    }

    @JsonProperty("Metar")
    public void setMetar(String metar) {
        this.metar = metar;
    }

    @JsonProperty("NXMetro")
    public String getNXMetro() {
        return nXMetro;
    }

    @JsonProperty("NXMetro")
    public void setNXMetro(String nXMetro) {
        this.nXMetro = nXMetro;
    }

    @JsonProperty("NXState")
    public String getNXState() {
        return nXState;
    }

    @JsonProperty("NXState")
    public void setNXState(String nXState) {
        this.nXState = nXState;
    }

    @JsonProperty("Population")
    public Object getPopulation() {
        return population;
    }

    @JsonProperty("Population")
    public void setPopulation(Object population) {
        this.population = population;
    }

    @JsonProperty("PrimaryWarningCountyCode")
    public String getPrimaryWarningCountyCode() {
        return primaryWarningCountyCode;
    }

    @JsonProperty("PrimaryWarningCountyCode")
    public void setPrimaryWarningCountyCode(String primaryWarningCountyCode) {
        this.primaryWarningCountyCode = primaryWarningCountyCode;
    }

    @JsonProperty("PrimaryWarningZoneCode")
    public String getPrimaryWarningZoneCode() {
        return primaryWarningZoneCode;
    }

    @JsonProperty("PrimaryWarningZoneCode")
    public void setPrimaryWarningZoneCode(String primaryWarningZoneCode) {
        this.primaryWarningZoneCode = primaryWarningZoneCode;
    }

    @JsonProperty("Satellite")
    public String getSatellite() {
        return satellite;
    }

    @JsonProperty("Satellite")
    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    @JsonProperty("Synoptic")
    public String getSynoptic() {
        return synoptic;
    }

    @JsonProperty("Synoptic")
    public void setSynoptic(String synoptic) {
        this.synoptic = synoptic;
    }

    @JsonProperty("MarineStation")
    public String getMarineStation() {
        return marineStation;
    }

    @JsonProperty("MarineStation")
    public void setMarineStation(String marineStation) {
        this.marineStation = marineStation;
    }

    @JsonProperty("MarineStationGMTOffset")
    public Object getMarineStationGMTOffset() {
        return marineStationGMTOffset;
    }

    @JsonProperty("MarineStationGMTOffset")
    public void setMarineStationGMTOffset(Object marineStationGMTOffset) {
        this.marineStationGMTOffset = marineStationGMTOffset;
    }

    @JsonProperty("VideoCode")
    public String getVideoCode() {
        return videoCode;
    }

    @JsonProperty("VideoCode")
    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    @JsonProperty("PartnerID")
    public Object getPartnerID() {
        return partnerID;
    }

    @JsonProperty("PartnerID")
    public void setPartnerID(Object partnerID) {
        this.partnerID = partnerID;
    }

    @JsonProperty("Sources")
    public List<Source> getSources() {
        return sources;
    }

    @JsonProperty("Sources")
    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    @JsonProperty("CanonicalPostalCode")
    public String getCanonicalPostalCode() {
        return canonicalPostalCode;
    }

    @JsonProperty("CanonicalPostalCode")
    public void setCanonicalPostalCode(String canonicalPostalCode) {
        this.canonicalPostalCode = canonicalPostalCode;
    }

    @JsonProperty("CanonicalLocationKey")
    public String getCanonicalLocationKey() {
        return canonicalLocationKey;
    }

    @JsonProperty("CanonicalLocationKey")
    public void setCanonicalLocationKey(String canonicalLocationKey) {
        this.canonicalLocationKey = canonicalLocationKey;
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
