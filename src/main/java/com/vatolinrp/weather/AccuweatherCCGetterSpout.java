package com.vatolinrp.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.weather.model.WeatherConditionTO;
import com.vatolinrp.weather.model.accuweather.WeatherElement;
import com.vatolinrp.weather.util.StormConstants;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Accuweather current condition getter spout
 */
public class AccuweatherCCGetterSpout extends BaseRichSpout implements StormConstants {
  public static final String ID = "current-accuweather-cond";
  private static final Logger logger = Logger.getLogger( AccuweatherCCGetterSpout.class.getName() );
  private static final String TRANSFER_VALUE = "weatherConditionTO-AW";

  private SpoutOutputCollector spoutOutputCollector;
  private static RestTemplate restTemplate;
  private static ObjectMapper objectMapper;

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    spoutOutputCollector = collector;
    restTemplate = new RestTemplate();
    objectMapper = new ObjectMapper();
  }
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare( new Fields( TRANSFER_VALUE ) );
  }

  public void nextTuple() {
    for( CitiesEnum city : CitiesEnum.values() ) {
      try {
        String url = createURL( city );
        logger.info("requesting data using this url: " + url );
        String response = restTemplate.getForObject( url, String.class );
        WeatherElement weatherElement = objectMapper.readValue( response, WeatherElement[].class)[0];
        WeatherConditionTO weatherConditionTO = creareTO( weatherElement, city );
        spoutOutputCollector.emit( new Values( weatherConditionTO ) );
        logger.info( String.format( "current condition from accuweather sent further with value: %s",
          weatherConditionTO.toString() ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    Utils.sleep( SLEEP_TIME );
  }

  private String createURL( CitiesEnum citiesEnum ) {
    return String.format( "http://" + ACCUWEATHER_HOST + "/currentconditions/v1/%s?apikey=%s",
      citiesEnum.getCityId(), ACCCUWEATHER_API_KEY );
  }

  private WeatherConditionTO creareTO( WeatherElement weatherElement, CitiesEnum citiesEnum ) {
    WeatherConditionTO weatherConditionTO = new WeatherConditionTO();
    weatherConditionTO.setTemperature( weatherElement.getTemperature().getImperial().getValue() );
    weatherConditionTO.setLocationKey( citiesEnum.getCityId() );
    ZonedDateTime date = ZonedDateTime.parse( weatherElement.getLocalObservationDateTime() );
    weatherConditionTO.setTargetDate( date );
    weatherConditionTO.setApiType( ACCUWEATHER_API_TYPE );
    String key = citiesEnum.getCityId() + "&" + date.getDayOfMonth() + "&" + date.getHour() + "&" + ACCUWEATHER_API_TYPE;
    weatherConditionTO.setTransferKey( key );
    return weatherConditionTO;
  }
}
