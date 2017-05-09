package com.vatolinrp.weather;

import com.vatolinrp.weather.model.AccuracyEnum;
import com.vatolinrp.weather.model.AccuracyResult;
import com.vatolinrp.weather.model.ApiEnum;
import com.vatolinrp.weather.model.CitiesEnum;
import com.vatolinrp.weather.model.HourAccuracy;
import com.vatolinrp.weather.util.StormConstants;
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

public class AccuracyCollectorBolt extends BaseRichBolt implements StormConstants
{
  private static final Logger logger = Logger.getLogger( AccuracyCollectorBolt.class.getName() );
  public static final String ID = "data-collector";
  private CacheManager cacheManager;

  @Override
  public void prepare( Map stormConf, TopologyContext context, OutputCollector collector ) {
    cacheManager = CacheManager.newInstance();
    createCounters();
  }

  private void createCounters() {
    Cache cache = cacheManager.getCache( ACCURACY_COUNTERS_CACHE_NAME );
    for( ApiEnum apiEnumValue : ApiEnum.values() ) {
      for( CitiesEnum citiesEnumValue : CitiesEnum.values() ) {
        AccuracyResult accuracyResult = new AccuracyResult( apiEnumValue, citiesEnumValue.getCityId() );
        String key = "accuracy_counter:" + apiEnumValue.getCode() + "&" + citiesEnumValue.getCityId();
        cache.put( new Element( key, accuracyResult ) );
      }
    }
  }

  @Override
  public void execute( Tuple tuple ) {
    HourAccuracy hourAccuracy = (HourAccuracy)tuple.getValueByField( "hourAccuracy" );
    String key = "accuracy_counter:" + hourAccuracy.getApiType().getCode() + "&" + hourAccuracy.getLocationKey();
    Cache cache = cacheManager.getCache( ACCURACY_COUNTERS_CACHE_NAME );
    Element element = cache.get( key );
    if( element.getObjectValue() instanceof AccuracyResult ) {
      AccuracyResult accuracyResult = (AccuracyResult)element.getObjectValue();
      Double temperatureDiff = Math.abs( hourAccuracy.getExpectedTemperature() - hourAccuracy.getActualTemperature() );
      switch ( AccuracyEnum.getAccuracyByFahrenheitDiff( temperatureDiff ) ) {
        case ACCURATE: {
          accuracyResult.incrementAccurateCounter();
          break;
        }
        case CLOSE_TO_ACCURATE: {
          accuracyResult.incrementCloseToAccurateCounter();
          break;
        }
        case NOT_ACCURATE: {
          accuracyResult.incrementNotAccurateCounter();
          break;
        }
        default: {}
      }
      cache.put( new Element( key, accuracyResult ) );
    }


  }

  @Override
  public void declareOutputFields( OutputFieldsDeclarer declarer ) { }
}
