package delta.games.lotro.character.stats.base;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Stats contributions from hope.
 * @author DAM
 */
public class HopeStatsContributionsMgr
{
  private static final float[] MORALE_FRACTION = {
      // Dread: -15 -> -1
      -0.99f, -0.97f, -0.95f, -0.90f, -0.85f, -0.80f, -0.65f,
      -0.60f, -0.50f, -0.40f, -0.30f, -0.20f, -0.15f, -0.10f, -0.05f,
      // Neutral
      0,
      // Hope: 1 -> 15
      0.01f, 0.02f, 0.03f, 0.04f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f,
      0.05f, 0.05f, 0.05f, 0.05f, 0.05f, 0.05f
  };

  /**
   * Get contribution.
   * @param stats Raw stats.
   * @return Contributed stats.
   */
  public BasicStatsSet getContribution(BasicStatsSet stats)
  {
    BasicStatsSet ret=new BasicStatsSet();
    FixedDecimalsInteger hopeStat=stats.getStat(STAT.HOPE);
    if (hopeStat != null)
    {
      FixedDecimalsInteger morale=stats.getStat(STAT.MORALE);
      int hope=hopeStat.intValue();
      if (hope!=0)
      {
        float factor=MORALE_FRACTION[hope+15];
        FixedDecimalsInteger moraleContrib=new FixedDecimalsInteger(morale);
        moraleContrib.multiply(new FixedDecimalsInteger(factor));
        ret.setStat(STAT.MORALE,moraleContrib);
      }
    }
    return ret;
  }
}
