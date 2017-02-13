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
  private static final String MINSK_CANNONICAL_LOCATION_KEY = "28580";
  private static final String URL = "http://apidev.accuweather.com/currentconditions/v1/%s.json?language=en&apikey=%s";
  private static final String CITIES_NEARBY = "http://apidev.accuweather.com/locations/v1/cities/neighbors/%s.json?apikey=%s&language=en&details=true";
  private static final Long MINUTE = 60000L;

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    spoutOutputCollector = collector;
  }

  public void nextTuple() {
    RestTemplate restTemplate = new RestTemplate();
    String response = restTemplate.getForObject( URL, String.class );
    ObjectMapper objectMapper = new ObjectMapper();
    Double temperature = .0;
    if( LocalDateTime.now().getMinute() == 0 ) {
      try {
        WeatherElement weatherElement = objectMapper.readValue( response, WeatherElement[].class )[0];
        temperature = weatherElement.getTemperature().getMetric().getValue();
      } catch (IOException e) {
        e.printStackTrace();
      }
      spoutOutputCollector.emit( new Values( temperature ) );
      Utils.sleep( MINUTE );
    }
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare( new Fields( "temperature" ) );
  }

  private Map<String, Double> getTemperatureForLocation( List<Neighbour> neighbourList ) {
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();
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
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();
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
