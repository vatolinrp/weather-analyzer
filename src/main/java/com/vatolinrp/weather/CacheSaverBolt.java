package com.vatolinrp.weather;

import com.vatolinrp.weather.model.WeatherConditionTO;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;


import java.util.Map;
import java.util.logging.Logger;

public class CacheSaverBolt extends BaseRichBolt {
  public static final String ID = "cache-saver";
  private CacheManager cacheManager;
  private static final Logger logger = Logger.getLogger( CacheSaverBolt.class.getName() );

  @Override
  public void prepare( Map stormConf, TopologyContext context, OutputCollector collector ) {
    cacheManager = CacheManager.newInstance();
  }

  @Override
  public void execute( Tuple tuple ) {
    WeatherConditionTO weatherConditionTO = null;
    if( tuple.contains( "weatherConditionTO-AW" ) ) {
      weatherConditionTO = ( WeatherConditionTO )tuple.getValueByField("weatherConditionTO-AW" );
    }
    if( tuple.contains( "weatherConditionTO-DS" ) ) {
      weatherConditionTO = ( WeatherConditionTO )tuple.getValueByField("weatherConditionTO-DS" );
    }
    if( weatherConditionTO == null ) {
      return;
    }
    Cache cache = cacheManager.getCache( "hourForecast" );
    cache.put( new Element( weatherConditionTO.getTransferKey(), weatherConditionTO ) );
    logger.info( String.format( "cache populated with key : %s and value: %s",
      weatherConditionTO.getTransferKey(), weatherConditionTO.toString() ) );
  }

  /**
   * We do no output here, because its only put Bolt
   */
  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) { }
}
