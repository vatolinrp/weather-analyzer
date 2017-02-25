package com.vatolinrp.weather;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.weather.model.WeatherConditionTO;
import com.vatolinrp.weather.model.darksky.DarkSkyResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

public class DarkSkyCCGetterSpout extends BaseRichSpout {
  public static final String ID = "current-darksky-cond";
  private static final Logger logger = Logger.getLogger( DarkSkyCCGetterSpout.class.getName() );
  private static final String DARK_SKY_API_KEY = "70038fb5200adc067f7bd899890ee0cf";
  private static final String DARK_SKY_HOST = "api.darksky.net";
  private static final String MINSK_LAT = "53.9";
  private static final String MINSK_LON = "27.576";
  private static final String URL = "https://" + DARK_SKY_HOST + "/forecast/" + DARK_SKY_API_KEY + "/"+MINSK_LAT+","
    + MINSK_LON +",%s?exclude=daily,hourly,flags";
  private static final String TRANSFER_VALUE = "weatherConditionTO-DS";
  private static final Long TEN_MINUTES = 600000L;
  private static final Long THREE_HOURS = 10800L;
  private static final String MINSK_CANNONICAL_LOCATION_KEY = "28580";
  private SpoutOutputCollector spoutOutputCollector;
  private static RestTemplate restTemplate;
  private static ObjectMapper objectMapper;

  @Override
  public void open( Map map, TopologyContext topologyContext, SpoutOutputCollector collector ) {
    spoutOutputCollector = collector;
    restTemplate = new RestTemplate();
    objectMapper = new ObjectMapper();
  }

  @Override
  public void nextTuple() {
    try {
      Long unixTime = System.currentTimeMillis() / 1000L + THREE_HOURS;
      String response = restTemplate.getForObject( String.format( URL, unixTime ), String.class );
      DarkSkyResponse weatherElement = objectMapper.readValue( response, DarkSkyResponse.class );
      WeatherConditionTO weatherConditionTO;
      weatherConditionTO = new WeatherConditionTO();
      weatherConditionTO.setTemperature( weatherElement.getCurrently().getTemperature() );
      weatherConditionTO.setLocationKey( MINSK_CANNONICAL_LOCATION_KEY );
      Date date = new Date( weatherElement.getCurrently().getTime() * 1000L );
      ZonedDateTime zonedDateTime = date.toInstant().atZone( ZoneId.of( "Europe/Minsk" ) );
      weatherConditionTO.setTargetDate( zonedDateTime );
      String key = MINSK_CANNONICAL_LOCATION_KEY + "&" + zonedDateTime.getDayOfMonth() + "&" + zonedDateTime.getHour() + "&DS";
      weatherConditionTO.setTransferKey( key );
      weatherConditionTO.setApiType( "DS" );
      spoutOutputCollector.emit( new Values( weatherConditionTO ) );
      logger.info( String.format( "current condition from dark sky api sent further with value: %s",
        weatherConditionTO.toString() ) );
    } catch (IOException e) {
      e.printStackTrace();
    }
    Utils.sleep( TEN_MINUTES );
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare( new Fields( TRANSFER_VALUE ) );
  }
}
