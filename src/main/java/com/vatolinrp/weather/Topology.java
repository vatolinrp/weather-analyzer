package com.vatolinrp.weather;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

public class Topology {
  private static final String TOPOLOGY_NAME = "weather_analyzer";

  public static void main(String[] args) {
    Config config = new Config();
    config.put( Config.TOPOLOGY_DEBUG, true );
    config.put( Config.TOPOLOGY_WORKERS, 1 );
    config.put( Config.TOPOLOGY_MAX_SPOUT_PENDING, 200 );
    TopologyBuilder topologyBuilder = new TopologyBuilder();
    TemperatureSpout temperatureSpout = new TemperatureSpout();
    topologyBuilder.setSpout( TemperatureSpout.ID, temperatureSpout ).setNumTasks( 1 ).setMaxSpoutPending( 250 );
    MailSenderBolt mailSenderBolt = new MailSenderBolt();
    topologyBuilder.setBolt( MailSenderBolt.ID, mailSenderBolt ).shuffleGrouping( TemperatureSpout.ID );
    StormTopology stormTopology = topologyBuilder.createTopology();
    LocalCluster localCluster = new LocalCluster();
    localCluster.submitTopology( TOPOLOGY_NAME, config, stormTopology );
  }
}
