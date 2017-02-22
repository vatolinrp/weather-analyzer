package com.vatolinrp.weather;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.weather.model.WeatherConditionTransferObject;
import com.vatolinrp.weather.model.WeatherElement;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Accuweather current condition getter spout
 */
public class AccuweatherCCGetterSpout extends BaseRichSpout {
  private static final String ACCUWEATHER_HOST = "dataservice.accuweather.com";
  private static final String URL = "http://" + ACCUWEATHER_HOST + "/currentconditions/v1/%s?apikey=%s";
  private static final String PERSONAL_API_KEY = "nlodiXHXlW4DYOOnld3dAGbigT9A6hav";
  private static final String MINSK_CANNONICAL_LOCATION_KEY = "28580";
  private static final String TRANSFER_KEY = "transfer_key";
  private static final String TRANSFER_VALUE = "currentConditionTransferObject";
  private static final Long MINUTE = 60000L;
  private SpoutOutputCollector spoutOutputCollector;
  private static RestTemplate restTemplate;
  private static ObjectMapper objectMapper;

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    spoutOutputCollector = collector;
    restTemplate = new RestTemplate();
    objectMapper = new ObjectMapper();
  }
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare( new Fields( TRANSFER_KEY, TRANSFER_VALUE ) );
  }

  public void nextTuple() {
    if( LocalDateTime.now().getMinute() == 0 ) {
      try {
        String response = restTemplate.getForObject( String.format( URL,
          MINSK_CANNONICAL_LOCATION_KEY, PERSONAL_API_KEY ), String.class );
        WeatherElement weatherElement = objectMapper.readValue( response, WeatherElement[].class )[0];
        WeatherConditionTransferObject weatherConditionTransferObject;
        weatherConditionTransferObject = new WeatherConditionTransferObject();
        weatherConditionTransferObject.setTemperature( weatherElement.getTemperature().getImperial().getValue() );
        weatherConditionTransferObject.setLocationKey( MINSK_CANNONICAL_LOCATION_KEY );
        ZonedDateTime date = ZonedDateTime.parse( weatherElement.getLocalObservationDateTime() );
        weatherConditionTransferObject.setTargetDate( date );
        String key = MINSK_CANNONICAL_LOCATION_KEY + "&" + date.getDayOfMonth() + "&" + date.getHour();
        spoutOutputCollector.emit( new Values( key, weatherConditionTransferObject) );
      } catch (IOException e) {
        e.printStackTrace();
      }
      Utils.sleep( MINUTE );
    }
  }
}
