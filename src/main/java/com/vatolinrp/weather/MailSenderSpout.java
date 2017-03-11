package com.vatolinrp.weather;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import com.vatolinrp.weather.model.HourAccuracy;
import com.vatolinrp.weather.util.CalculationUtil;
import com.vatolinrp.weather.util.StormConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class MailSenderSpout extends BaseRichSpout implements StormConstants {

  private static final Logger logger = Logger.getLogger( MailSenderSpout.class.getName() );
  public static final String ID = "mail-sender";
  private URL reportFileUrl;

  private Properties mailProperties;
  private CacheManager cacheManager;
  private MailAuthenticator mailAuthenticator;

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    mailProperties = new Properties();
    mailProperties.put( "mail.smtp.host", "smtp.mail.ru" );
    mailProperties.put( "mail.smtp.auth", "true" );
    mailProperties.put( "mail.smtp.port", "587" );
    mailProperties.put( "mail.smtp.starttls.enable", "true" );
    mailAuthenticator = new MailAuthenticator();
    cacheManager = CacheManager.newInstance();
    reportFileUrl = getClass().getClassLoader().getResource( REPORT_FILE_NAME );
  }

  public void nextTuple() {
    try{
      if( LocalDateTime.now().getMinute() == 0 ) {
        createCSVReportFile();
        sendEmailWithReport();
        deleteCSVReportFile();
      }
      Thread.sleep( SLEEP_TIME );
    } catch ( Exception ex ) {
      ex.printStackTrace();
    }
  }

  /**
   * removes just created report file
   */
  private void deleteCSVReportFile() {
    if ( new File( reportFileUrl.getFile() ).delete() ) {
      logger.info("successfully deleted csv file");
    } else {
      logger.info("file was not deleted");
    }
  }

  /**
   * sending email with report file inside
   */
  private void sendEmailWithReport() throws MessagingException, IOException {
    Session session = Session.getInstance( mailProperties, mailAuthenticator );
    MimeMessage message = new MimeMessage( session );
    message.setFrom( new InternetAddress( MAIL_FROM ) );
    message.addRecipient( Message.RecipientType.TO, new InternetAddress( MAIL_TO_ADDRESS ) );
    message.setSubject( COMMON_MESSAGE_SUBJECT );
    BodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setText( "Report mail" );
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(messageBodyPart);
    messageBodyPart = new MimeBodyPart();
    String filename = new File( reportFileUrl.getFile() ).getAbsolutePath();
    DataSource source = new FileDataSource( filename );
    messageBodyPart.setDataHandler( new DataHandler( source ) );
    messageBodyPart.setFileName( REPORT_FILE_NAME );
    multipart.addBodyPart(messageBodyPart);
    message.setContent( multipart );
    Transport.send( message );
  }

  /**
   * Getting all objects from report cache
   * and saving them to csv file
   */
  private void createCSVReportFile() throws IOException {
    Cache cache = cacheManager.getCache( REPORT_CACHE_NAME );
    List keys = cache.getKeys();
    try ( FileWriter fileWriter = new FileWriter( REPORT_FILE_LOCATION ) ) {
      fileWriter.append( "Location, Date, Hour, Forecast(F), Real(F), Forecast(C), Real(C), Weather Service\n" );
      for( Object key: keys ) {
        Element element = cache.get( key );
        logger.info("getting object from reports for key: " + key );
        if( element.getObjectValue() instanceof HourAccuracy ) {
          HourAccuracy hourAccuracy = (HourAccuracy)element.getObjectValue();
          insertHourAccuracy( fileWriter, hourAccuracy );
        }
      }
    }
    logger.info( "successfully created csv report file" );
  }
  private void insertHourAccuracy( FileWriter fileWriter, HourAccuracy hourAccuracy ) throws IOException {
    fileWriter.append( CitiesEnum.getNameByCityId( hourAccuracy.getLocationKey() ).toString() );
    fileWriter.append( ',' );
    fileWriter.append( hourAccuracy.getDate().toString() );
    fileWriter.append( ',' );
    fileWriter.append( hourAccuracy.getHour().toString() );
    fileWriter.append( ',' );
    fileWriter.append( hourAccuracy.getExpectedTemperature().toString() );
    fileWriter.append( ',' );
    fileWriter.append( hourAccuracy.getActualTemperature().toString() );
    fileWriter.append( ',' );
    fileWriter.append( CalculationUtil.round( CalculationUtil.getCelsius( hourAccuracy.getExpectedTemperature() ) ).toString() );
    fileWriter.append( ',' );
    fileWriter.append( CalculationUtil.round( CalculationUtil.getCelsius( hourAccuracy.getActualTemperature() ) ).toString() );
    fileWriter.append( ',' );
    fileWriter.append( hourAccuracy.getApiType() );
    fileWriter.append("\n");
    fileWriter.flush();
  }
  public void declareOutputFields( OutputFieldsDeclarer declarer ) {}
}
