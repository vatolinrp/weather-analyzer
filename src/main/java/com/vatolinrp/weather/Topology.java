package com.vatolinrp.weather;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

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
    MailSenderSpout mailSenderSpout = new MailSenderSpout();
    DarkSkyCCGetterSpout darkSkyCCGetterSpout = new DarkSkyCCGetterSpout();
    DarkSkyFCGetterSpout darkSkyFCGetterSpout = new DarkSkyFCGetterSpout();
    topologyBuilder.setSpout( MailSenderSpout.ID, mailSenderSpout ).setNumTasks( 1 ).setMaxSpoutPending( 250 );
    topologyBuilder.setSpout( DarkSkyCCGetterSpout.ID, darkSkyCCGetterSpout ).setNumTasks( 1 ).setMaxSpoutPending( 250 );
    topologyBuilder.setSpout( DarkSkyFCGetterSpout.ID, darkSkyFCGetterSpout ).setNumTasks( 1 ).setMaxSpoutPending( 250 );
    topologyBuilder.setSpout( AccuweatherCCGetterSpout.ID, accuweatherCCGetterSpout ).setNumTasks( 1 ).setMaxSpoutPending( 250 );
    topologyBuilder.setSpout( AccuweatherFCGetterSpout.ID, accuweatherFCGetterSpout ).setNumTasks( 1 ).setMaxSpoutPending( 250 );
    AggregatorBolt aggregatorBolt = new AggregatorBolt();
    CacheSaverBolt cacheSaverBolt = new CacheSaverBolt();
    DataCollectorBolt dataCollectorBolt = new DataCollectorBolt();
    topologyBuilder.setBolt( CacheSaverBolt.ID, cacheSaverBolt )
      .shuffleGrouping( AccuweatherFCGetterSpout.ID ).shuffleGrouping( DarkSkyFCGetterSpout.ID );
    topologyBuilder.setBolt( AggregatorBolt.ID, aggregatorBolt)
      .shuffleGrouping( AccuweatherCCGetterSpout.ID ).shuffleGrouping( DarkSkyCCGetterSpout.ID );
    topologyBuilder.setBolt( DataCollectorBolt.ID, dataCollectorBolt ).shuffleGrouping( AggregatorBolt.ID );
    AccuracyCollectorBolt accuracyCollectorBolt = new AccuracyCollectorBolt();
    topologyBuilder.setBolt( AccuracyCollectorBolt.ID, accuracyCollectorBolt ).shuffleGrouping( DataCollectorBolt.ID );
    StormTopology stormTopology = topologyBuilder.createTopology();
    LocalCluster localCluster = new LocalCluster();
    localCluster.submitTopology( TOPOLOGY_NAME, config, stormTopology );
  }
}
