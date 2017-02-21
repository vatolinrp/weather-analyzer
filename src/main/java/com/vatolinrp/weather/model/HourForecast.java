
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
    "DateTime",
    "EpochDateTime",
    "WeatherIcon",
    "IconPhrase",
    "IsDaylight",
    "Temperature",
    "RealFeelTemperature",
    "WetBulbTemperature",
    "DewPoint",
    "Wind",
    "WindGust",
    "RelativeHumidity",
    "Visibility",
    "Ceiling",
    "UVIndex",
    "UVIndexText",
    "PrecipitationProbability",
    "RainProbability",
    "SnowProbability",
    "IceProbability",
    "TotalLiquid",
    "Rain",
    "Snow",
    "Ice",
    "CloudCover",
    "MobileLink",
    "Link"
})
public class HourForecast {

    @JsonProperty("DateTime")
    private String dateTime;
    @JsonProperty("EpochDateTime")
    private Integer epochDateTime;
    @JsonProperty("WeatherIcon")
    private Integer weatherIcon;
    @JsonProperty("IconPhrase")
    private String iconPhrase;
    @JsonProperty("IsDaylight")
    private Boolean isDaylight;
    @JsonProperty("Temperature")
    private ForecastTemperature temperature;
    @JsonProperty("RealFeelTemperature")
    private RealFeelTemperature realFeelTemperature;
    @JsonProperty("WetBulbTemperature")
    private WetBulbTemperature wetBulbTemperature;
    @JsonProperty("DewPoint")
    private DewPoint dewPoint;
    @JsonProperty("Wind")
    private Wind wind;
    @JsonProperty("WindGust")
    private WindGust windGust;
    @JsonProperty("RelativeHumidity")
    private Integer relativeHumidity;
    @JsonProperty("Visibility")
    private Visibility visibility;
    @JsonProperty("Ceiling")
    private Ceiling ceiling;
    @JsonProperty("UVIndex")
    private Integer uVIndex;
    @JsonProperty("UVIndexText")
    private String uVIndexText;
    @JsonProperty("PrecipitationProbability")
    private Integer precipitationProbability;
    @JsonProperty("RainProbability")
    private Integer rainProbability;
    @JsonProperty("SnowProbability")
    private Integer snowProbability;
    @JsonProperty("IceProbability")
    private Integer iceProbability;
    @JsonProperty("TotalLiquid")
    private TotalLiquid totalLiquid;
    @JsonProperty("Rain")
    private Rain rain;
    @JsonProperty("Snow")
    private Snow snow;
    @JsonProperty("Ice")
    private Ice ice;
    @JsonProperty("CloudCover")
    private Integer cloudCover;
    @JsonProperty("MobileLink")
    private String mobileLink;
    @JsonProperty("Link")
    private String link;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("DateTime")
    public String getDateTime() {
        return dateTime;
    }

    @JsonProperty("DateTime")
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @JsonProperty("EpochDateTime")
    public Integer getEpochDateTime() {
        return epochDateTime;
    }

    @JsonProperty("EpochDateTime")
    public void setEpochDateTime(Integer epochDateTime) {
        this.epochDateTime = epochDateTime;
    }

    @JsonProperty("WeatherIcon")
    public Integer getWeatherIcon() {
        return weatherIcon;
    }

    @JsonProperty("WeatherIcon")
    public void setWeatherIcon(Integer weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    @JsonProperty("IconPhrase")
    public String getIconPhrase() {
        return iconPhrase;
    }

    @JsonProperty("IconPhrase")
    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

    @JsonProperty("IsDaylight")
    public Boolean getIsDaylight() {
        return isDaylight;
    }

    @JsonProperty("IsDaylight")
    public void setIsDaylight(Boolean isDaylight) {
        this.isDaylight = isDaylight;
    }

    @JsonProperty("Temperature")
    public ForecastTemperature getTemperature() {
        return temperature;
    }

    @JsonProperty("Temperature")
    public void setTemperature(ForecastTemperature temperature) {
        this.temperature = temperature;
    }

    @JsonProperty("RealFeelTemperature")
    public RealFeelTemperature getRealFeelTemperature() {
        return realFeelTemperature;
    }

    @JsonProperty("RealFeelTemperature")
    public void setRealFeelTemperature(RealFeelTemperature realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    @JsonProperty("WetBulbTemperature")
    public WetBulbTemperature getWetBulbTemperature() {
        return wetBulbTemperature;
    }

    @JsonProperty("WetBulbTemperature")
    public void setWetBulbTemperature(WetBulbTemperature wetBulbTemperature) {
        this.wetBulbTemperature = wetBulbTemperature;
    }

    @JsonProperty("DewPoint")
    public DewPoint getDewPoint() {
        return dewPoint;
    }

    @JsonProperty("DewPoint")
    public void setDewPoint(DewPoint dewPoint) {
        this.dewPoint = dewPoint;
    }

    @JsonProperty("Wind")
    public Wind getWind() {
        return wind;
    }

    @JsonProperty("Wind")
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @JsonProperty("WindGust")
    public WindGust getWindGust() {
        return windGust;
    }

    @JsonProperty("WindGust")
    public void setWindGust(WindGust windGust) {
        this.windGust = windGust;
    }

    @JsonProperty("RelativeHumidity")
    public Integer getRelativeHumidity() {
        return relativeHumidity;
    }

    @JsonProperty("RelativeHumidity")
    public void setRelativeHumidity(Integer relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    @JsonProperty("Visibility")
    public Visibility getVisibility() {
        return visibility;
    }

    @JsonProperty("Visibility")
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @JsonProperty("Ceiling")
    public Ceiling getCeiling() {
        return ceiling;
    }

    @JsonProperty("Ceiling")
    public void setCeiling(Ceiling ceiling) {
        this.ceiling = ceiling;
    }

    @JsonProperty("UVIndex")
    public Integer getUVIndex() {
        return uVIndex;
    }

    @JsonProperty("UVIndex")
    public void setUVIndex(Integer uVIndex) {
        this.uVIndex = uVIndex;
    }

    @JsonProperty("UVIndexText")
    public String getUVIndexText() {
        return uVIndexText;
    }

    @JsonProperty("UVIndexText")
    public void setUVIndexText(String uVIndexText) {
        this.uVIndexText = uVIndexText;
    }

    @JsonProperty("PrecipitationProbability")
    public Integer getPrecipitationProbability() {
        return precipitationProbability;
    }

    @JsonProperty("PrecipitationProbability")
    public void setPrecipitationProbability(Integer precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    @JsonProperty("RainProbability")
    public Integer getRainProbability() {
        return rainProbability;
    }

    @JsonProperty("RainProbability")
    public void setRainProbability(Integer rainProbability) {
        this.rainProbability = rainProbability;
    }

    @JsonProperty("SnowProbability")
    public Integer getSnowProbability() {
        return snowProbability;
    }

    @JsonProperty("SnowProbability")
    public void setSnowProbability(Integer snowProbability) {
        this.snowProbability = snowProbability;
    }

    @JsonProperty("IceProbability")
    public Integer getIceProbability() {
        return iceProbability;
    }

    @JsonProperty("IceProbability")
    public void setIceProbability(Integer iceProbability) {
        this.iceProbability = iceProbability;
    }

    @JsonProperty("TotalLiquid")
    public TotalLiquid getTotalLiquid() {
        return totalLiquid;
    }

    @JsonProperty("TotalLiquid")
    public void setTotalLiquid(TotalLiquid totalLiquid) {
        this.totalLiquid = totalLiquid;
    }

    @JsonProperty("Rain")
    public Rain getRain() {
        return rain;
    }

    @JsonProperty("Rain")
    public void setRain(Rain rain) {
        this.rain = rain;
    }

    @JsonProperty("Snow")
    public Snow getSnow() {
        return snow;
    }

    @JsonProperty("Snow")
    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    @JsonProperty("Ice")
    public Ice getIce() {
        return ice;
    }

    @JsonProperty("Ice")
    public void setIce(Ice ice) {
        this.ice = ice;
    }

    @JsonProperty("CloudCover")
    public Integer getCloudCover() {
        return cloudCover;
    }

    @JsonProperty("CloudCover")
    public void setCloudCover(Integer cloudCover) {
        this.cloudCover = cloudCover;
    }

    @JsonProperty("MobileLink")
    public String getMobileLink() {
        return mobileLink;
    }

    @JsonProperty("MobileLink")
    public void setMobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
    }

    @JsonProperty("Link")
    public String getLink() {
        return link;
    }

    @JsonProperty("Link")
    public void setLink(String link) {
        this.link = link;
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
