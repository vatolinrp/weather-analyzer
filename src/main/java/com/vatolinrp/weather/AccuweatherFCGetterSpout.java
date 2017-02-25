package com.vatolinrp.weather;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.weather.model.HourForecast;
import com.vatolinrp.weather.model.WeatherConditionTO;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.logging.Logger;

public class AccuweatherFCGetterSpout extends BaseRichSpout {
  private static final Logger logger = Logger.getLogger( AccuweatherFCGetterSpout.class.getName() );
  public static final String ID = "forecast-condition-getter";
  private SpoutOutputCollector spoutOutputCollector;
  private static RestTemplate restTemplate;
  private static ObjectMapper objectMapper;
  private static final String ACCUWEATHER_HOST = "dataservice.accuweather.com";
  private static final String TRANSFER_VALUE = "weatherConditionTO";
  private static final Long MINUTE = 60000L;
  private static final String MINSK_CANNONICAL_LOCATION_KEY = "28580";
  private static final String PERSONAL_API_KEY = "nlodiXHXlW4DYOOnld3dAGbigT9A6hav";
  private static final String HOUR_FORECAST_URL = "http://" + ACCUWEATHER_HOST + "/forecasts/v1/hourly/1hour/%s?apikey=%s";

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    spoutOutputCollector = collector;
    restTemplate = new RestTemplate();
    objectMapper = new ObjectMapper();
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare( new Fields( TRANSFER_VALUE ) );
  }

  public void nextTuple() {
      try {
        String response = restTemplate.getForObject( String.format( HOUR_FORECAST_URL,
          MINSK_CANNONICAL_LOCATION_KEY, PERSONAL_API_KEY ), String.class );
        HourForecast weatherElement = objectMapper.readValue( response, HourForecast[].class )[0];
        WeatherConditionTO weatherConditionTO;
        weatherConditionTO = new WeatherConditionTO();
        weatherConditionTO.setTemperature( weatherElement.getTemperature().getValue().doubleValue() );
        weatherConditionTO.setLocationKey( MINSK_CANNONICAL_LOCATION_KEY );
        ZonedDateTime date = ZonedDateTime.parse( weatherElement.getDateTime() );
        weatherConditionTO.setTargetDate( date );
        String key = MINSK_CANNONICAL_LOCATION_KEY + "&" + date.getDayOfMonth() + "&" + date.getHour();
        weatherConditionTO.setTransferKey( key );
        spoutOutputCollector.emit( new Values( weatherConditionTO ) );
        logger.info( String.format( "forecast condition sent further with value: %s", weatherConditionTO.toString() ) );
      } catch (IOException e) {
        e.printStackTrace();
      }
      Utils.sleep( MINUTE );
  }
}
