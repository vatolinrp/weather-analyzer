package com.vatolinrp.weather;

import com.vatolinrp.weather.model.AccuracyEnum;
import com.vatolinrp.weather.model.AccuracyResult;
import com.vatolinrp.weather.model.ApiEnum;
import com.vatolinrp.weather.model.CitiesEnum;
import com.vatolinrp.weather.model.HourAccuracy;
import com.vatolinrp.weather.util.CalculationUtil;
import com.vatolinrp.weather.util.StormConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.utils.Utils;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class MailSenderSpout extends BaseRichSpout implements StormConstants {

  private static final Logger logger = Logger.getLogger( MailSenderSpout.class.getName() );
  public static final String ID = "mail-sender";
  private URL reportFileUrl;
  private static int numberOfReports;
  private Properties mailProperties;
  private CacheManager cacheManager;
  private MailAuthenticator mailAuthenticator;
  private static final Long MINUTE_SLEEP_TIME = 60000L;
  private static final String GREETING_IMAGE_LOCATION = "src/main/resources/images/greeting_message.jpg";
  private static final String REPORT_IMAGE_LOCATION = "src/main/resources/images/report_message.jpg";
  private DataSource greetingImageResource;
  private DataSource reportImageResource;

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    mailProperties = new Properties();
    mailProperties.put( "mail.smtp.host", "smtp.mail.ru" );
    mailProperties.put( "mail.smtp.auth", "true" );
    mailProperties.put( "mail.smtp.port", "587" );
    mailProperties.put( "mail.smtp.starttls.enable", "true" );
    mailAuthenticator = new MailAuthenticator();
    cacheManager = CacheManager.newInstance();
    greetingImageResource = new FileDataSource( GREETING_IMAGE_LOCATION );
    reportImageResource = new FileDataSource( REPORT_IMAGE_LOCATION );
    sendGreetingMessage();
  }

  private void sendGreetingMessage() {
    try {
      Session session = Session.getInstance( mailProperties, mailAuthenticator );
      Message message = new MimeMessage( session );
      message.setFrom( new InternetAddress( MAIL_FROM ) );
      message.addRecipient( Message.RecipientType.TO, new InternetAddress( MAIL_TO_ADDRESS ) );
      message.setSubject( "Greeting message" );
      MimeMultipart multipart = new MimeMultipart("related");
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent("<img src=\"cid:image\">", "text/html");
      multipart.addBodyPart( messageBodyPart );
      messageBodyPart = new MimeBodyPart();
      messageBodyPart.setDataHandler( new DataHandler( greetingImageResource ) );
      messageBodyPart.setHeader("Content-ID", "<image>");
      multipart.addBodyPart(messageBodyPart);
      message.setContent(multipart);
      Transport.send( message );
      logger.info("Email has been successfully sent" );
    } catch ( MessagingException e ) {
      throw new RuntimeException( "Not able to send report emails. Quitting the application", e );
    }
  }

  public void nextTuple() {
    try{
      if( createCSVReportFile() ) {
        sendEmailWithReport();
        deleteCSVReportFile();
      }
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
      reportFileUrl = null;
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
    messageBodyPart.setContent("<img src=\"cid:image\">", "text/html");
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart( messageBodyPart );
    messageBodyPart = new MimeBodyPart();
    String filename = new File( reportFileUrl.getFile() ).getAbsolutePath();
    DataSource source = new FileDataSource( filename );
    messageBodyPart.setDataHandler( new DataHandler( source ) );
    messageBodyPart.setFileName( REPORT_FILE_NAME );
    multipart.addBodyPart( messageBodyPart );
    messageBodyPart = new MimeBodyPart();
    messageBodyPart.setDataHandler( new DataHandler( reportImageResource ) );
    messageBodyPart.setHeader("Content-ID", "<image>");
    multipart.addBodyPart( messageBodyPart );
    message.setContent( multipart );
    Transport.send( message );
    logger.info( "Email has been successfully sent" );
  }

  /**
   * Getting all objects from report cache
   * and saving them to csv file
   */
  private boolean createCSVReportFile() throws IOException {
    Cache reportCache = cacheManager.getCache( REPORT_CACHE_NAME );
    Cache accuracyCache = cacheManager.getCache( ACCURACY_COUNTERS_CACHE_NAME );
    List keys = reportCache.getKeys();
    if( keys.size() == numberOfReports ) {
      return false;
    }
    try ( FileWriter fileWriter = new FileWriter( REPORT_FILE_LOCATION ) ) {
      fileWriter.append( "Location, Date, Hour, Temperature forecast(F), Temperature real(F), Temperature forecast(C)," +
        " Temperature real(C), Wind speed forecast(mi/h), Wind speed real(mi/h), Wind speed forecast(km/h)," +
        " Wind speed real(km/h), Weather service, Accuracy for temperature, Accuracy for wind speed, \n" );
      for( Object key: keys ) {
        if( reportCache.isKeyInCache( key ) ) {
          Element element = reportCache.get( key );
          if( element != null && element.getObjectValue() instanceof HourAccuracy ) {
            logger.info("getting object from reports for key: " + key );
            HourAccuracy hourAccuracy = (HourAccuracy)element.getObjectValue();
            insertHourAccuracy( fileWriter, hourAccuracy );
          }
        }
      }
      fileWriter.append( "Location, Best weather service \n" );
      for( CitiesEnum cityEnumValue : CitiesEnum.values() ) {
        Map<Double, ApiEnum> accuracyMap = new HashMap<>();
        for( ApiEnum apiEnumValue : ApiEnum.values() ) {
          String accuracyTemperatureKey = "accuracy_counter:" + apiEnumValue.getCode() + "&" + cityEnumValue.getCityId() + "&" + "temperature";
          if( accuracyCache.isKeyInCache( accuracyTemperatureKey ) ) {
            Element element = accuracyCache.get( accuracyTemperatureKey );
            if( element != null && element.getObjectValue() instanceof AccuracyResult ) {
              AccuracyResult accuracyResult = (AccuracyResult)element.getObjectValue();
              accuracyMap.put( accuracyResult.getAccuracyPercent(), apiEnumValue );
            }
          }
        }
        Double bestPercent = .0;
        for( Double currentPercent : accuracyMap.keySet() ) {
          if( currentPercent >= bestPercent ) {
            bestPercent = currentPercent;
          }
        }
        fileWriter.append( cityEnumValue + "," + accuracyMap.get( bestPercent ).getName() + "," + "for temperature" + "," + bestPercent + "\n" );
      }
      for( CitiesEnum cityEnumValue : CitiesEnum.values() ) {
        Map<Double, ApiEnum> accuracyMap = new HashMap<>();
        for( ApiEnum apiEnumValue : ApiEnum.values() ) {
          String accuracyWindSpeedKey = "accuracy_counter:" + apiEnumValue.getCode() + "&" + cityEnumValue.getCityId() + "&" + "windSpeed";
          if( accuracyCache.isKeyInCache( accuracyWindSpeedKey ) ) {
            Element element = accuracyCache.get( accuracyWindSpeedKey );
            if( element != null && element.getObjectValue() instanceof AccuracyResult ) {
              AccuracyResult accuracyResult = (AccuracyResult)element.getObjectValue();
              accuracyMap.put( accuracyResult.getAccuracyPercent(), apiEnumValue );
            }
          }
        }
        Double bestPercent = .0;
        for( Double currentPercent : accuracyMap.keySet() ) {
          if( currentPercent >= bestPercent ) {
            bestPercent = currentPercent;
          }
        }
        fileWriter.append( cityEnumValue + "," + accuracyMap.get( bestPercent ).getName() + "," + "for wind speed" + "," + bestPercent + "\n" );
      }
    }
    logger.info( "successfully created csv report file" );
    numberOfReports = reportCache.getKeys().size();
    reportFileUrl = getClass().getClassLoader().getResource( REPORT_FILE_NAME );
    return true;
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
    fileWriter.append( hourAccuracy.getExpectedWindSpeed().toString() );
    fileWriter.append( ',' );
    fileWriter.append( hourAccuracy.getActualWindSpeed().toString() );
    fileWriter.append( ',' );
    fileWriter.append( CalculationUtil.round( CalculationUtil.getKms( hourAccuracy.getExpectedWindSpeed() ) ).toString() );
    fileWriter.append( ',' );
    fileWriter.append( CalculationUtil.round( CalculationUtil.getKms( hourAccuracy.getActualWindSpeed() ) ).toString() );
    fileWriter.append( ',' );
    fileWriter.append( hourAccuracy.getApiType().getCode() );
    fileWriter.append( ',' );
    Double temperatureDiff = Math.abs( hourAccuracy.getExpectedTemperature() - hourAccuracy.getActualTemperature() );
    fileWriter.append( AccuracyEnum.getAccuracyByFahrenheitDiff( temperatureDiff ).toString() );
    fileWriter.append( ',' );
    Double windSpeedDiff = Math.abs( hourAccuracy.getExpectedWindSpeed() - hourAccuracy.getActualWindSpeed() );
    fileWriter.append( AccuracyEnum.getAccuracyByMilesPerHourDiff( windSpeedDiff ).toString() );
    fileWriter.append("\n");
    fileWriter.flush();
  }
  public void declareOutputFields( OutputFieldsDeclarer declarer ) {}
}
