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
    AccuweatherCCGetterSpout accuweatherCCGetterSpout = new AccuweatherCCGetterSpout();
    AccuweatherFCGetterSpout accuweatherFCGetterSpout = new AccuweatherFCGetterSpout();
    topologyBuilder.setSpout( AccuweatherCCGetterSpout.ID, accuweatherCCGetterSpout ).setNumTasks( 1 ).setMaxSpoutPending( 250 );
    topologyBuilder.setSpout( AccuweatherFCGetterSpout.ID, accuweatherFCGetterSpout ).setNumTasks( 1 ).setMaxSpoutPending( 250 );
    MailSenderBolt mailSenderBolt = new MailSenderBolt();
    CacheExtractorBolt cacheExtractorBolt = new CacheExtractorBolt();
    CacheSaverBolt cacheSaverBolt = new CacheSaverBolt();
    topologyBuilder.setBolt( CacheSaverBolt.ID, cacheSaverBolt ).shuffleGrouping( AccuweatherFCGetterSpout.ID );
    topologyBuilder.setBolt( CacheExtractorBolt.ID, cacheExtractorBolt ).shuffleGrouping( AccuweatherCCGetterSpout.ID );
    topologyBuilder.setBolt( MailSenderBolt.ID, mailSenderBolt ).shuffleGrouping( CacheExtractorBolt.ID );
    StormTopology stormTopology = topologyBuilder.createTopology();
    LocalCluster localCluster = new LocalCluster();
    localCluster.submitTopology( TOPOLOGY_NAME, config, stormTopology );
  }
}
