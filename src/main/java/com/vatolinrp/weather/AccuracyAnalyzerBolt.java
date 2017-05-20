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

public class AccuracyAnalyzerBolt extends BaseRichBolt implements StormConstants
{
  private static final Logger logger = Logger.getLogger( AccuracyAnalyzerBolt.class.getName() );
  public static final String ID = "accuracy-analyzer";
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
    String temperatureKey = "accuracy_counter:" + hourAccuracy.getApiType().getCode() + "&" + hourAccuracy.getLocationKey() + "&" + "temperature";
    String windSpeedKey = "accuracy_counter:" + hourAccuracy.getApiType().getCode() + "&" + hourAccuracy.getLocationKey() + "&" + "windSpeed";
    Cache cache = cacheManager.getCache( ACCURACY_COUNTERS_CACHE_NAME );
    Element element = cache.get( temperatureKey );
    if( element.getObjectValue() instanceof AccuracyResult ) {
      AccuracyResult accuracyResult = (AccuracyResult)element.getObjectValue();
      Double temperatureDiff = Math.abs( hourAccuracy.getExpectedTemperature() - hourAccuracy.getActualTemperature() );
      switch ( AccuracyEnum.getAccuracyByFahrenheitDiff( temperatureDiff ) ) {
        case ACCURATE_TEMPERATURE: {
          accuracyResult.incrementAccurateCounter();
          break;
        }
        case CLOSE_TO_ACCURATE_TEMPERATURE: {
          accuracyResult.incrementCloseToAccurateCounter();
          break;
        }
        case NOT_ACCURATE_TEMPERATURE: {
          accuracyResult.incrementNotAccurateCounter();
          break;
        }
        default: {}
      }
      cache.put( new Element( temperatureKey, accuracyResult ) );
    }
    element = cache.get( windSpeedKey );
    if( element.getObjectValue() instanceof AccuracyResult ) {
      AccuracyResult accuracyResult = (AccuracyResult)element.getObjectValue();
      Double windSpeedDiff = Math.abs( hourAccuracy.getExpectedWindSpeed() - hourAccuracy.getActualWindSpeed() );
      switch ( AccuracyEnum.getAccuracyByMilesPerHourDiff( windSpeedDiff ) ) {
        case ACCURATE_WIND_SPEED: {
          accuracyResult.incrementAccurateCounter();
          break;
        }
        case CLOSE_TO_ACCURATE_WIND_SPEED: {
          accuracyResult.incrementCloseToAccurateCounter();
          break;
        }
        case NOT_ACCURATE_WIND_SPEED: {
          accuracyResult.incrementNotAccurateCounter();
          break;
        }
        default: {}
      }
      cache.put( new Element( windSpeedKey, accuracyResult ) );
    }


  }

  @Override
  public void declareOutputFields( OutputFieldsDeclarer declarer ) { }
}
