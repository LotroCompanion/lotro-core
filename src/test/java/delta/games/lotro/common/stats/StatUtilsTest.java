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
    StatDescription stat=new StatDescription(0);
    stat.setPercentage(false);
    sampleTest(Integer.valueOf(2),stat,"2");
    sampleTest(Integer.valueOf(-2),stat,"-2");
    sampleTest(Float.valueOf(2.1f),stat,"2");
    sampleTest(Float.valueOf(-2.1f),stat,"-2");
    sampleTest(Float.valueOf(2.99f),stat,"3");
    sampleTest(Float.valueOf(-2.99f),stat,"-3");
    sampleTest(Float.valueOf(-0.75f),stat,"-0.8");
  }

  private void sampleTest(Number value, StatDescription stat, String expected)
  {
    String valueStr=StatUtils.getStatDisplay(value,stat);
    Assert.assertEquals(expected,valueStr);
  }
}
