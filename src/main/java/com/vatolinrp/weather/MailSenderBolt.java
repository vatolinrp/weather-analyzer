package com.vatolinrp.weather;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.vatolinrp.weather.model.WeatherConditionTO;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class MailSenderBolt extends BaseRichBolt {

  private static final Logger logger = Logger.getLogger( MailSenderBolt.class.getName() );
  public static final String ID = "mail-sender";
  private static final Double MITTENS_REQUIRED = 15.;
  private static final Double FUR_COAT_REQUIRED = .0;
  private static boolean isLocked = false;
  private Properties mailProperties;

  private OutputCollector outputCollector;

  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    outputCollector = collector;
    mailProperties = new Properties();
    mailProperties.put( "mail.smtp.host", "smtp.mail.ru" );
    mailProperties.put( "mail.smtp.auth", "true" );
    mailProperties.put( "mail.smtp.port", "587" );
    mailProperties.put( "mail.smtp.starttls.enable", "true" );
  }

  public void execute(Tuple input) {
    WeatherConditionTO currentCondition = (WeatherConditionTO)input.getValueByField( "currentCondition" );
    WeatherConditionTO forecastCondition = (WeatherConditionTO)input.getValueByField( "forecastCondition" );

    Session session = Session.getInstance(mailProperties,
      new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication("vatolinrp@mail.ru", System.getenv("MAIL_PASSWORD" ) );
        }
      }
    );

    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress("vatolinrp@mail.ru"));
      message.addRecipient( Message.RecipientType.TO, new InternetAddress("vatolinrp@icloud.com" ) );
      message.setSubject( "Weather in Minsk" );
      StringBuffer buffer = new StringBuffer();
      buffer.append( "Temperature in Minsk is: " );
      buffer.append( currentCondition.getTemperature() );
      buffer.append( "F\n");
      buffer.append( "Forecast told us: " );
      buffer.append( forecastCondition.getTemperature() );
      buffer.append( "F\n");
      if( currentCondition.getTemperature().equals(forecastCondition.getTemperature() ) ) {
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

  public void declareOutputFields( OutputFieldsDeclarer declarer ) {}
}
