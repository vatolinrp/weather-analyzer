package com.vatolinrp.weather;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.weather.model.WeatherElement;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class TemperatureSpout extends BaseRichSpout {

  public static final String ID = "temperature-reader";
  private SpoutOutputCollector spoutOutputCollector;
  private static final String URL = "http://apidev.accuweather.com/currentconditions/v1/28580.json?language=en&apikey=hoArfRosT1215";
  private static final Long MINUTE = 60000L;

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    spoutOutputCollector = collector;
  }

  public void nextTuple() {
    RestTemplate restTemplate = new RestTemplate();
    String response = restTemplate.getForObject( URL, String.class );
    ObjectMapper objectMapper = new ObjectMapper();
    Double temperature = .0;
    if( LocalDateTime.now().getMinute() == 0 ) {
      try {
        WeatherElement weatherElement = objectMapper.readValue( response, WeatherElement[].class )[0];
        temperature = weatherElement.getTemperature().getMetric().getValue();
      } catch (IOException e) {
        e.printStackTrace();
      }
      spoutOutputCollector.emit( new Values( temperature ) );
      Utils.sleep( MINUTE );
    }
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare( new Fields( "temperature" ) );
  }
}
