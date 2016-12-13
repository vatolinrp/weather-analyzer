package com.vatolinrp.weather;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

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

  private OutputCollector outputCollector;

  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    outputCollector = collector;
  }

  public void execute(Tuple input) {
    Double temperatureReceived = (Double)input.getValueByField( "temperature" );
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.mail.ru" );
    properties.put("mail.smtp.auth", "true" );
    properties.put("mail.smtp.port", "587" );
    properties.put("mail.smtp.starttls.enable", "true");
    Session session = Session.getInstance(properties,
      new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication("vatolinrp@mail.ru", System.getenv("MAIL_PASSWORD" ) );
        }
      }
    );

    try {
      MimeMessage message = new MimeMessage(session);

      message.setFrom(new InternetAddress("vatolinrp@mail.ru"));

      message.addRecipient(Message.RecipientType.TO, new InternetAddress("vatolinrp@icloud.com"));
      message.setSubject( "Weather in Minsk" );
      message.setText("Temperature in Minsk is: " + temperatureReceived + "C" );
      Transport.send(message);

      if( LocalDateTime.now().getHour() == 9 ) {
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress("vatolinrp@mail.ru"));
        message.addRecipient( Message.RecipientType.TO, new InternetAddress("vatolinrp@icloud.com"));
        message.addRecipient( Message.RecipientType.TO, new InternetAddress("mire-natasha@yandex.ru"));
        message.addRecipient( Message.RecipientType.TO, new InternetAddress("russan2005@yandex.ru"));
        message.setSubject( "Weather in Minsk" );
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append( "Temperature in Minsk is: " + temperatureReceived + "C" );
        stringBuffer.append( "\n" );
        if (temperatureReceived < MITTENS_REQUIRED) {
          stringBuffer.append( "Put on mittens, please." );
          stringBuffer.append( "\n" );
        }
        if (temperatureReceived < FUR_COAT_REQUIRED) {

          stringBuffer.append( "Put on fur coat, please." );
          stringBuffer.append( "\n" );
        }
        message.setText(stringBuffer.toString());
        Transport.send(message);
        isLocked = true;
      }
      if( LocalDateTime.now().getHour() != 9 ) {
        isLocked = false;
      }
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
    logger.info( "temperature received : " +  temperatureReceived );
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {

  }
}
