package delta.games.lotro.common.stats;

import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.maths.Progression;

/**
 * Scalable stat value provider.
 * @author DAM
 */
public class ScalableStatProvider extends AbstractStatProvider
{
  private Progression _progression;

  /**
   * Constructor.
   * @param stat Targeted stat.
   * @param progression Stat value.
   */
  public ScalableStatProvider(STAT stat, Progression progression)
  {
    super(stat);
    _progression=progression;
  }

  /**
   * Get the progression.
   * @return the progression.
   */
  public Progression getProgression()
  {
    return _progression;
  }

  @Override
  public Float getStatValue(int tier, int level)
  {
    Float ret=_progression.getValue(level);
    if (ret!=null)
    {
      // Fix values if needed
      ret=Float.valueOf(StatUtils.fixStatValue(getStat(),ret.floatValue()));
    }
    return ret;
  }
}
