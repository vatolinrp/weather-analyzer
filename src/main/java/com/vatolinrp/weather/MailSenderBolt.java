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
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class MailSenderBolt extends BaseRichBolt {

  private static final Logger logger = Logger.getLogger( MailSenderBolt.class.getName() );
  public static final String ID = "mail-sender";

  private OutputCollector outputCollector;

  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    outputCollector = collector;
  }

  public void execute(Tuple input) {
    String temperatureReceived = (String)input.getValueByField( "temperature" );
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
      message.setText("Temperature in Minsk is: " + temperatureReceived );
      Transport.send(message);
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
    logger.info( "temperature received : " +  temperatureReceived );
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {

  }
}
