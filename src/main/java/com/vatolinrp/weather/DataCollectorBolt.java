package com.vatolinrp.weather;

import com.vatolinrp.weather.model.HourAccuracy;
import com.vatolinrp.weather.model.WeatherConditionTO;
import com.vatolinrp.weather.util.StormConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.logging.Logger;

public class DataCollectorBolt extends BaseRichBolt implements StormConstants
{
  private static final Logger logger = Logger.getLogger( DataCollectorBolt.class.getName() );
  public static final String ID = "data-collector";
  private CacheManager cacheManager;
  private OutputCollector boltOutputCollector;

  @Override
  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    cacheManager = CacheManager.newInstance();
    boltOutputCollector = outputCollector;
  }

  @Override
  public void execute( Tuple tuple ) {
    WeatherConditionTO currentCondition = null;
    WeatherConditionTO forecastCondition = null;
    if( tuple.contains( "currentCondition" ) ) {
      currentCondition = ( WeatherConditionTO )tuple.getValueByField("currentCondition" );
    }
    if( tuple.contains( "forecastCondition" ) ) {
      forecastCondition = ( WeatherConditionTO )tuple.getValueByField("forecastCondition" );
    }
    if( currentCondition == null || forecastCondition == null ) {
      return;
    }
    Cache cache = cacheManager.getCache( REPORT_CACHE_NAME );
    HourAccuracy hourAccuracy = new HourAccuracy();
    hourAccuracy.setDate( currentCondition.getTargetDate().toLocalDate() );
    hourAccuracy.setLocationKey( currentCondition.getLocationKey() );
    hourAccuracy.setActualTemperature( currentCondition.getTemperature() );
    hourAccuracy.setExpectedTemperature( forecastCondition.getTemperature() );
    hourAccuracy.setHour( currentCondition.getTargetDate().getHour() );
    hourAccuracy.setApiType( currentCondition.getApiType() );
    String key = hourAccuracy.getDate().toString() + "&" + hourAccuracy.getHour().toString()
      + "&" + hourAccuracy.getLocationKey() + "&" + hourAccuracy.getApiType();
    cache.put( new Element( key, hourAccuracy ) );
    logger.info( String.format( "DataCollectorBolt populated cache with key : %s and value: %s",
      key, hourAccuracy.toString() ) );
    boltOutputCollector.emit( new Values( "notification" ) );
  }

  @Override
  public void declareOutputFields( OutputFieldsDeclarer outputFieldsDeclarer ) {
    outputFieldsDeclarer.declare( new Fields("notification" ) );
  }
}
