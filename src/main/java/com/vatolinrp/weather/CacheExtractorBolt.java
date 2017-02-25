package com.vatolinrp.weather;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.vatolinrp.weather.model.WeatherConditionTO;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.Map;
import java.util.logging.Logger;

public class CacheExtractorBolt extends BaseRichBolt {

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
    WeatherConditionTO weatherConditionTO = ( WeatherConditionTO )tuple.getValueByField("weatherConditionTO" );
    logger.info( String.format( "received a tuple with transfer key: %s",weatherConditionTO.getTransferKey() ) );
    Cache cache = cacheManager.getCache( "hourForecast" );
    if( cache.isKeyInCache( weatherConditionTO.getTransferKey() ) ) {
      boltOutputCollector.emit( new Values( weatherConditionTO.getTransferKey(), weatherConditionTO ) );
      Element element = cache.get( weatherConditionTO.getTransferKey() );
      if( element != null ) {
        weatherConditionTO = ( WeatherConditionTO )element.getObjectValue();
        logger.info( String.format( "got from cache value : %s", weatherConditionTO.toString() ) );
        boltOutputCollector.emit( new Values( weatherConditionTO.getTransferKey(), weatherConditionTO ) );
      }
    }
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare( new Fields("transferKey", "weatherConditionTO" ) );
  }
}
