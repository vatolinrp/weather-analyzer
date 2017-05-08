package com.vatolinrp.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.weather.model.accuweather.HourForecast;
import com.vatolinrp.weather.model.WeatherConditionTO;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class AccuweatherFCGetterSpout extends BaseRichSpout implements StormConstants {
  private static final Logger logger = Logger.getLogger( AccuweatherFCGetterSpout.class.getName() );
  public static final String ID = "forecast-accuweather-cond";
  private SpoutOutputCollector spoutOutputCollector;
  private static RestTemplate restTemplate;
  private static ObjectMapper objectMapper;
  private static final String TRANSFER_VALUE = "weatherConditionTO-AW";
  private static AtomicInteger forecastRequestsExecuted;

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    spoutOutputCollector = collector;
    restTemplate = new RestTemplate();
    objectMapper = new ObjectMapper();
    forecastRequestsExecuted = new AtomicInteger(0);
  }

  public void declareOutputFields( OutputFieldsDeclarer declarer ) {
    declarer.declare( new Fields( TRANSFER_VALUE ) );
  }

  public void nextTuple() {
    for( CitiesEnum city : CitiesEnum.values() ) {
      try {
        String url = createURL( city );
        logger.info("requesting data using this url: " + url );
        String response = restTemplate.getForObject( url, String.class );
        logger.info("total number of forecast request executions is " + forecastRequestsExecuted.incrementAndGet() );
        HourForecast hourForecast = objectMapper.readValue(response, HourForecast[].class)[0];
        WeatherConditionTO weatherConditionTO = creareTO( hourForecast, city );
        spoutOutputCollector.emit( new Values( weatherConditionTO ) );
        logger.info( String.format( "forecast condition from accuweather sent further with value: %s",
          weatherConditionTO.toString()));
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    Utils.sleep( SLEEP_TIME );
  }

  private String createURL( CitiesEnum citiesEnum ) {
    return String.format( "http://" + ACCUWEATHER_HOST + "/forecasts/v1/hourly/1hour/%s?apikey=%s",
        citiesEnum.getCityId(), ACCCUWEATHER_API_KEY );
  }

  private WeatherConditionTO creareTO(HourForecast hourForecast, CitiesEnum city ) {
    WeatherConditionTO weatherConditionTO = new WeatherConditionTO();
    weatherConditionTO.setTemperature( hourForecast.getTemperature().getValue().doubleValue() );
    weatherConditionTO.setLocationKey( city.getCityId() );
    ZonedDateTime date = ZonedDateTime.parse( hourForecast.getDateTime() );
    weatherConditionTO.setTargetDate( date );
    weatherConditionTO.setApiType( ACCUWEATHER_API_TYPE );
    String key = city.getCityId() + "&" + date.getDayOfMonth() + "&" + date.getHour() + "&" + ACCUWEATHER_API_TYPE;
    weatherConditionTO.setTransferKey(key);
    return weatherConditionTO;
  }
}
