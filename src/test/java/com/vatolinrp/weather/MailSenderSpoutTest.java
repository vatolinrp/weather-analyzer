package com.vatolinrp.weather;

import com.vatolinrp.weather.model.HourAccuracy;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;

/**
 * This test is aimed to check if mail was send
 */
public class MailSenderSpoutTest {

  @BeforeMethod
  public void setUp() throws Exception {
    CacheManager cacheManager = CacheManager.newInstance();
    Cache cache = cacheManager.getCache( "reports" );
    HourAccuracy hourAccuracy = new HourAccuracy();
    hourAccuracy.setApiType( "test api type" );
    hourAccuracy.setHour( 13 );
    hourAccuracy.setLocationKey( "28580" );
    hourAccuracy.setExpectedTemperature( 1. );
    hourAccuracy.setActualTemperature( .0 );
    hourAccuracy.setDate( LocalDate.now() );
    Element element = new Element( "test-key", hourAccuracy );
    cache.put( element );
  }

  /**
   * Run this test and check mail box for new messages
   */
  @Test
  public void testMessageSendingFunctionality() throws IOException, InterruptedException {
    MailSenderSpout mailSenderSpout = new MailSenderSpout();
    mailSenderSpout.open( null,null,null );
    mailSenderSpout.nextTuple();
    mailSenderSpout.nextTuple();
    mailSenderSpout.nextTuple();
    mailSenderSpout.nextTuple();
    mailSenderSpout.nextTuple();
    mailSenderSpout.nextTuple();
    mailSenderSpout.nextTuple();
    mailSenderSpout.nextTuple();
  }
}