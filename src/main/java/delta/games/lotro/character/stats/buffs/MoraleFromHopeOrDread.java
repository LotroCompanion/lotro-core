package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Stats contributions from hope/dread.
 * @author DAM
 */
public class MoraleFromHopeOrDread extends AbstractBuffImpl
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

  @Override
  public BasicStatsSet getStats(CharacterData character, BasicStatsSet raw, BuffInstance buff)
  {
    BasicStatsSet ret=new BasicStatsSet();
    FixedDecimalsInteger hopeStat=raw.getStat(WellKnownStat.HOPE);
    if (hopeStat != null)
    {
      FixedDecimalsInteger morale=raw.getStat(WellKnownStat.MORALE);
      int hope=hopeStat.intValue();
      if (hope!=0)
      {
        float factor=MORALE_FRACTION[hope+15];
        FixedDecimalsInteger moraleContrib=new FixedDecimalsInteger(morale);
        moraleContrib.multiply(new FixedDecimalsInteger(factor));
        ret.setStat(WellKnownStat.MORALE,moraleContrib);
      }
    }
    return ret;
  }
}
