package com.vatolinrp.weather;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.vatolinrp.weather.model.HourAccuracy;
import com.vatolinrp.weather.model.WeatherConditionTO;
import com.vatolinrp.weather.util.StormConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class MailSenderBolt extends BaseRichBolt implements StormConstants {

  private static final Logger logger = Logger.getLogger( MailSenderBolt.class.getName() );
  public static final String ID = "mail-sender";

  private Properties mailProperties;
  private CacheManager cacheManager;
  private MailAuthenticator mailAuthenticator;

  public void prepare( Map stormConf, TopologyContext context, OutputCollector collector ) {
    mailProperties = new Properties();
    mailProperties.put( "mail.smtp.host", "smtp.mail.ru" );
    mailProperties.put( "mail.smtp.auth", "true" );
    mailProperties.put( "mail.smtp.port", "587" );
    mailProperties.put( "mail.smtp.starttls.enable", "true" );
    mailAuthenticator = new MailAuthenticator();
    cacheManager = CacheManager.newInstance();
  }

  public void execute( Tuple input ) {
    WeatherConditionTO currentCondition = (WeatherConditionTO)input.getValueByField( "currentCondition" );
    WeatherConditionTO forecastCondition = (WeatherConditionTO)input.getValueByField( "forecastCondition" );

    Session session = Session.getInstance( mailProperties, mailAuthenticator );
    Cache cache = cacheManager.getCache( "reportCache" );
    List keys = cache.getKeys();
    try {
      MimeMessage message = new MimeMessage( session );
      message.setFrom( new InternetAddress( MAIL_FROM ) );
      message.addRecipient( Message.RecipientType.TO, new InternetAddress("vatolinrp@icloud.com" ) );
      message.setSubject( "Weather Report" );
      StringBuffer buffer = new StringBuffer();
      for( Object key: keys ){
        Element element = cache.get( key );
        HourAccuracy hourAccuracy = ( HourAccuracy )element.getObjectValue();

        buffer.append( "Hour accuracy for " + CitiesEnum.getNameByCityId( hourAccuracy.getLocationKey() ) + ": \n" );
        buffer.append( "Data provider: \n" );
        if( ACCUWEATHER_API_TYPE.equals( forecastCondition.getApiType() ) ) {
          buffer.append( ACCUWEATHER_HOST + "\n" );
        }
        if( DARK_SKY_API_TYPE.equals( forecastCondition.getApiType() ) ) {
          buffer.append( DARK_SKY_HOST + "\n" );
        }
        buffer.append( "Date: " + hourAccuracy.getDate().toString() + ", Hour: " + hourAccuracy.getHour() + "\n" );
        buffer.append( "Current temperature (in F): " + hourAccuracy.getActualTemperature() + "F\n" );
        Double currentCelsius = getCelsius( hourAccuracy.getActualTemperature() );
        buffer.append( "Current temperature (in C): " + currentCelsius + "C\n" );
        buffer.append( "Expected temperature (in F): " + hourAccuracy.getExpectedTemperature() + "F\n" );
        Double expectedCelsius = getCelsius( hourAccuracy.getExpectedTemperature() );
        buffer.append( "Expected temperature (in C): " + expectedCelsius + "C\n" );
        buffer.append( "-----" );
      }

      if( currentCondition.getTemperature().equals( forecastCondition.getTemperature() ) ) {
        buffer.append( "Forecast was accurate for temperature prediction " );
      } else {
        buffer.append( "Forecast was not accurate for temperature prediction." );
        buffer.append( "It was reported. Will use better weather sources later on." );
      }
      message.setText( buffer.toString() );
      Transport.send( message );
      logger.info( "Successfully sent report message" );
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }

  private Double getCelsius( Double fahrenheit ) {
    return (fahrenheit - 32.)/1.8;
  }
  public void declareOutputFields( OutputFieldsDeclarer declarer ) {}
}
