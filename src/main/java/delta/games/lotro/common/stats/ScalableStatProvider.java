package delta.games.lotro.common.stats;

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
  public ScalableStatProvider(StatDescription stat, Progression progression)
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
    Float ret=null;
    if (_progression!=null)
    {
      ret=_progression.getValue(level);
      if (ret!=null)
      {
        // Fix values if needed
        ret=Float.valueOf(StatUtils.fixStatValue(getStat(),ret.floatValue()));
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return "Scalable stat provider: stat="+getStat().getName()+", progression="+_progression;
  }
}
