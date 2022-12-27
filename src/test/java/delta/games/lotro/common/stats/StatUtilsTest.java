package delta.games.lotro.common.stats;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test class for class StatUtils.
 * @author DAM
 */
public class StatUtilsTest extends TestCase
{
  /**
   * Test display of stat values.
   */
  public void testStatsDisplay()
  {
    sampleTest(Integer.valueOf(2),false,"2");
    sampleTest(Integer.valueOf(-2),false,"-2");
    sampleTest(Float.valueOf(2.1f),false,"2");
    sampleTest(Float.valueOf(-2.1f),false,"-2");
    sampleTest(Float.valueOf(2.99f),false,"3");
    sampleTest(Float.valueOf(-2.99f),false,"-3");
    sampleTest(Float.valueOf(-0.75f),false,"-0.8");
  }

  private void sampleTest(Number value, boolean percentage, String expected)
  {
    String valueStr=StatUtils.getStatDisplay(value,percentage);
    Assert.assertEquals(expected,valueStr);
  }
}
