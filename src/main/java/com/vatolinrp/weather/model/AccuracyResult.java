package com.vatolinrp.weather.model;

import lombok.Data;

import java.io.Serializable;

public @Data class AccuracyResult implements Serializable {
  private ApiEnum apiType;
  private String locationKey;
  private Integer accurateCounter;
  private Integer closeToAccurateCounter;
  private Integer notAccurateCounter;

  public AccuracyResult( ApiEnum apiType, String locationKey ) {
    this.apiType = apiType;
    this.locationKey = locationKey;
    this.accurateCounter = 0;
    this.closeToAccurateCounter = 0;
    this.notAccurateCounter = 0;
  }

  public void incrementAccurateCounter(){
    accurateCounter++;
  }

  public void incrementCloseToAccurateCounter() {
    closeToAccurateCounter++;
  }

  public void incrementNotAccurateCounter() {
    notAccurateCounter++;
  }

  public Double getAccuracyPercent() {
    if( accurateCounter == 0 && closeToAccurateCounter == 0 && notAccurateCounter == 0 ) {
      return .0;
    }
    return accurateCounter / (double)( accurateCounter + closeToAccurateCounter + notAccurateCounter );
  }
}
