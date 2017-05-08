package com.vatolinrp.weather;

import com.vatolinrp.weather.model.WeatherConditionTO;
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

public class CacheExtractorBolt extends BaseRichBolt {
  public static final String ID = "cache-extractor";
  private static final Logger logger = Logger.getLogger( CacheExtractorBolt.class.getName() );
  private OutputCollector boltOutputCollector;
  private CacheManager cacheManager;

  @Override
  public void prepare( Map map, TopologyContext topologyContext, OutputCollector outputCollector ) {
    cacheManager = CacheManager.newInstance();
    boltOutputCollector = outputCollector;
  }

  @Override
  public void execute( Tuple tuple ) {
    WeatherConditionTO currentWeatherConditionTO = null;
    if( tuple.contains( "weatherConditionTO-AW" ) ) {
      currentWeatherConditionTO = ( WeatherConditionTO )tuple.getValueByField("weatherConditionTO-AW" );
    }
    if( tuple.contains( "weatherConditionTO-DS" ) ) {
      currentWeatherConditionTO = ( WeatherConditionTO )tuple.getValueByField("weatherConditionTO-DS" );
    }
    if( currentWeatherConditionTO == null ) {
      return;
    }
    final String transferKey = currentWeatherConditionTO.getTransferKey();
    logger.info( String.format( "received a tuple with transfer key: %s", transferKey ) );
    Cache cache = cacheManager.getCache( "hourForecast" );
    if( cache.isKeyInCache( transferKey ) ) {
      Element element = cache.get( transferKey );
      if( element != null ) {
        WeatherConditionTO forecastWeatherConditionTO = ( WeatherConditionTO )element.getObjectValue();
        logger.info( String.format( "got from cache value : %s", forecastWeatherConditionTO.toString() ) );
        boltOutputCollector.emit( new Values( forecastWeatherConditionTO, currentWeatherConditionTO ) );
      }
    } else {
      logger.info( String.format( "no data found in cache for transaction key:%s", transferKey ) );
    }
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare( new Fields("forecastCondition", "currentCondition" ) );
  }
}
