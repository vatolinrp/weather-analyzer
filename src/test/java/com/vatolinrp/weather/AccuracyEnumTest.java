package com.vatolinrp.weather;

import com.vatolinrp.weather.model.AccuracyEnum;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccuracyEnumTest {

  @Test
  public void getAccuracyByFahrenheitDiffTest() {
    Assert.assertEquals(AccuracyEnum.getAccuracyByFahrenheitDiff(1.),AccuracyEnum.ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByFahrenheitDiff(.5),AccuracyEnum.ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByFahrenheitDiff(.8),AccuracyEnum.ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByFahrenheitDiff(1.2),AccuracyEnum.CLOSE_TO_ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByFahrenheitDiff(1.9),AccuracyEnum.CLOSE_TO_ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByFahrenheitDiff(2.),AccuracyEnum.CLOSE_TO_ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByFahrenheitDiff(2.1),AccuracyEnum.NOT_ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByFahrenheitDiff(2.9),AccuracyEnum.NOT_ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByFahrenheitDiff(4.),AccuracyEnum.NOT_ACCURATE );
  }

  @Test
  public void getAccuracyByCelsiusDiffTest() {
    Assert.assertEquals(AccuracyEnum.getAccuracyByCelsiusDiff(.1),AccuracyEnum.ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByCelsiusDiff(.4),AccuracyEnum.ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByCelsiusDiff(.5),AccuracyEnum.ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByCelsiusDiff(.6),AccuracyEnum.CLOSE_TO_ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByCelsiusDiff(.9),AccuracyEnum.CLOSE_TO_ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByCelsiusDiff(1.),AccuracyEnum.CLOSE_TO_ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByCelsiusDiff(1.1),AccuracyEnum.NOT_ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByCelsiusDiff(2.9),AccuracyEnum.NOT_ACCURATE );
    Assert.assertEquals(AccuracyEnum.getAccuracyByCelsiusDiff(4.),AccuracyEnum.NOT_ACCURATE );
  }
}
