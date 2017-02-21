package com.vatolinrp.weather;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.weather.model.Details;
import com.vatolinrp.weather.model.HourForecast;
import com.vatolinrp.weather.model.Neighbour;
import com.vatolinrp.weather.model.WeatherElement;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TemperatureSpout extends BaseRichSpout {

  public static final String ID = "temperature-reader";
  private SpoutOutputCollector spoutOutputCollector;
  private static final String API_KEY = "hoArfRosT1215";
  private static final String ACCUWEATHER_HOST = "dataservice.accuweather.com";
  private static final String PERSONAL_API_KEY = "nlodiXHXlW4DYOOnld3dAGbigT9A6hav";
  private static final String MINSK_CANNONICAL_LOCATION_KEY = "28580";
  private static final String URL = "http://" + ACCUWEATHER_HOST + "/currentconditions/v1/%s?apikey=%s";
  private static final String HOUR_FORECAST_URL = "http://" + ACCUWEATHER_HOST + "/forecasts/v1/hourly/1hour/%s?apikey=%s";
  private static final String CITIES_NEARBY = "http://apidev.accuweather.com/locations/v1/cities/neighbors/%s.json?apikey=%s&language=en&details=true";
  private static final Long MINUTE = 60000L;
  private static RestTemplate restTemplate;
  private static ObjectMapper objectMapper;

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    spoutOutputCollector = collector;
    restTemplate = new RestTemplate();
    objectMapper = new ObjectMapper();
  }

  public void nextTuple() {
    Double current = .0;
    Double inAnHour = .0;
    if( LocalDateTime.now().getMinute() == 0 ) {
      try {
        current = getCurrentTemperature(MINSK_CANNONICAL_LOCATION_KEY);
        inAnHour = getTemperatureInAnHour( MINSK_CANNONICAL_LOCATION_KEY);
      } catch (IOException e) {
        e.printStackTrace();
      }
      spoutOutputCollector.emit( new Values( current, inAnHour ) );
      Utils.sleep( MINUTE );
    }
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare( new Fields( "current","inAnHour" ) );
  }

  private Double getCurrentTemperature(String location) throws IOException {
    String response = restTemplate.getForObject( String.format( URL, location, PERSONAL_API_KEY ), String.class );
    WeatherElement weatherElement = objectMapper.readValue( response, WeatherElement[].class )[0];
    return weatherElement.getTemperature().getImperial().getValue();
  }

  private Double getTemperatureInAnHour(String location) throws IOException {
    String response = restTemplate.getForObject( String.format( HOUR_FORECAST_URL, location, PERSONAL_API_KEY ), String.class );
    HourForecast weatherElement = objectMapper.readValue( response, HourForecast[].class )[0];
    return weatherElement.getTemperature().getValue().doubleValue();
  }

  private Map<String, Double> getTemperatureForLocation( List<Neighbour> neighbourList ) {
    Map<String, Details> nameAndDetails = neighbourList.stream().collect(
      Collectors.toMap( Neighbour::getEnglishName, Neighbour::getDetails ) );
    Map<String, String> map = nameAndDetails.entrySet().stream().collect(
      Collectors.toMap( Map.Entry::getKey, e -> e.getValue().getCanonicalLocationKey() ) );
    Map< String, Double > nameAndTemperature = new HashMap<>();
    map.forEach((k,v)->{
      try {
        String response = restTemplate.getForObject( String.format( URL, v, API_KEY ), String.class );
        WeatherElement weatherElement = objectMapper.readValue(response, WeatherElement[].class)[0];
        nameAndTemperature.put( k, weatherElement.getTemperature().getMetric().getValue() );
      }  catch (IOException e) {
        e.printStackTrace();
      }

    });
    return nameAndTemperature;
  }
  
  private List<Neighbour> getNeigboursOfTheLocationId( String locationId ) {
    String citiesNearby = restTemplate.getForObject( String.format( CITIES_NEARBY, locationId, API_KEY ), String.class );
    List<Neighbour> neighbourList = null;
    try {
      neighbourList = Arrays.asList( objectMapper.readValue( citiesNearby, Neighbour[].class ) );
    } catch (IOException e) {
      e.printStackTrace();
    }
    return neighbourList;
  }
}
