package com.vatolinrp.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.weather.model.ApiEnum;
import com.vatolinrp.weather.model.CitiesEnum;
import com.vatolinrp.weather.model.WeatherConditionTO;
import com.vatolinrp.weather.model.darksky.DarkSkyResponse;
import com.vatolinrp.weather.util.StormConstants;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class DarkSkyCCGetterSpout extends BaseRichSpout implements StormConstants {
  public static final String ID = "current-darksky-cond";
  private static final Logger logger = Logger.getLogger( DarkSkyCCGetterSpout.class.getName() );
  private static final String TRANSFER_VALUE = "weatherConditionTO-DS";
  private static AtomicInteger currentCondRequestsExecuted;

  private SpoutOutputCollector spoutOutputCollector;
  private static RestTemplate restTemplate;
  private static ObjectMapper objectMapper;

  @Override
  public void open( Map map, TopologyContext topologyContext, SpoutOutputCollector collector ) {
    spoutOutputCollector = collector;
    restTemplate = new RestTemplate();
    objectMapper = new ObjectMapper();
    currentCondRequestsExecuted = new AtomicInteger(0);
  }

  @Override
  public void nextTuple() {
    for( CitiesEnum city : CitiesEnum.values() ) {
      try {
        String time = Long.toString( ZonedDateTime.now( ZoneId.of( city.getTimeZone() ) ).toEpochSecond());
        String url = createURL( city, time );
        logger.info("requesting data using this url: " + url );
        String response = restTemplate.getForObject( url, String.class );
        logger.info("total number of forecast request executions is " + currentCondRequestsExecuted.incrementAndGet() );
        DarkSkyResponse weatherElement = objectMapper.readValue( response, DarkSkyResponse.class );
        WeatherConditionTO weatherConditionTO = createTO( weatherElement, city );
        spoutOutputCollector.emit( new Values( weatherConditionTO ) );
        logger.info( String.format( "current condition from dark sky api sent further with value: %s",
          weatherConditionTO.toString() ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    Utils.sleep( SLEEP_TIME );
  }

  private WeatherConditionTO createTO( DarkSkyResponse darkSkyResponse, CitiesEnum citiesEnum ) {
    WeatherConditionTO weatherConditionTO = new WeatherConditionTO();
    weatherConditionTO.setTemperature( darkSkyResponse.getCurrently().getTemperature() );
    weatherConditionTO.setWindSpeed( darkSkyResponse.getCurrently().getWindSpeed() );
    weatherConditionTO.setLocationKey( citiesEnum.getCityId() );
    ZonedDateTime zonedDateTime = createTargetDate( darkSkyResponse, citiesEnum.getTimeZone() );
    weatherConditionTO.setTargetDate( zonedDateTime );
    String key = citiesEnum.getCityId() + "&" + zonedDateTime.getDayOfMonth() + "&"
      + zonedDateTime.getHour() + "&" + DARK_SKY_API_TYPE;
    weatherConditionTO.setTransferKey( key );
    weatherConditionTO.setApiType( ApiEnum.DS );
    return weatherConditionTO;
  }

  private ZonedDateTime createTargetDate( DarkSkyResponse darkSkyResponse, String timeZone ) {
    ZoneOffset zoneOffset = ZoneId.of( timeZone ).getRules().getOffset( Instant.EPOCH );
    LocalDateTime localDateTime = LocalDateTime.ofEpochSecond( darkSkyResponse.getCurrently().getTime(),0, zoneOffset );
    return localDateTime.atZone( ZoneId.of( timeZone ) );
  }

  private String createURL( CitiesEnum citiesEnum, String time ) {
    return "https://" + DARK_SKY_HOST + "/forecast/" + DARK_SKY_API_KEY + "/"+citiesEnum.getLatitude()+","
      + citiesEnum.getLongitude() +"," + time + "?exclude=daily,hourly,flags";
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare( new Fields( TRANSFER_VALUE ) );
  }
}
