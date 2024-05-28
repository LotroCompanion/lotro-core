package delta.games.lotro.common.stats;

import delta.common.utils.l10n.L10nConfiguration;
import delta.common.utils.l10n.LocalizedFormats;
import delta.common.utils.l10n.numbers.NumberFormatID;
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
    L10nConfiguration l10nConfiguration=new L10nConfiguration();
    l10nConfiguration.setNumberFormatID(NumberFormatID.US);
    LocalizedFormats.init(l10nConfiguration);

    FloatStatDescription stat=new FloatStatDescription();
    stat.setIdentifier(0);
    stat.setPercentage(false);
    stat.setMaxDigitsAbove1(0);
    stat.setMaxDigitsBelow1(2);
    sampleTest(Integer.valueOf(2),stat,"2");
    sampleTest(Integer.valueOf(-2),stat,"-2");
    sampleTest(Float.valueOf(2.1f),stat,"2");
    sampleTest(Float.valueOf(-2.1f),stat,"-2");
    sampleTest(Float.valueOf(2.99f),stat,"3");
    sampleTest(Float.valueOf(-2.99f),stat,"-3");
    sampleTest(Float.valueOf(-0.75f),stat,"-0.75");
  }

  private void sampleTest(Number value, StatDescription stat, String expected)
  {
    String valueStr=StatUtils.getStatDisplay(value,stat);
    Assert.assertEquals(expected,valueStr);
  }
}
