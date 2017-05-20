package com.vatolinrp.weather.util;

public interface StormConstants {
  String MAIL_FROM = "vatolinrp@mail.ru";
  String MAIL_FROM_PASSWORD = System.getenv("MAIL_PASSWORD" );
  String ACCUWEATHER_API_TYPE = "AW";
  String DARK_SKY_API_TYPE = "DS";
  Long SLEEP_TIME = 3600000L;
  String DARK_SKY_API_KEY = "70038fb5200adc067f7bd899890ee0cf";
  String ACCCUWEATHER_API_KEY = "5oOkHKsarUaCv0ubc1WhmD4jiDBR7kHa";
  String DARK_SKY_HOST = "api.darksky.net";
  String ACCUWEATHER_HOST = "dataservice.accuweather.com";
  Long HOUR = 3600L;
  String MAIL_TO_ADDRESS = "vatolinrp@icloud.com";
  String REPORT_CACHE_NAME = "reports";
  String ACCURACY_COUNTERS_CACHE_NAME = "accuracyCounters";
  String COMMON_MESSAGE_SUBJECT = "Weather report";
  String REPORT_FILE_NAME = "csvReportFile.csv";
  String REPORT_FILE_LOCATION = "target/classes/csvReportFile.csv";
}
