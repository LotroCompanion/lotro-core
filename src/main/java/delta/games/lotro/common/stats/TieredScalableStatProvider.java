package delta.games.lotro.common.stats;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.maths.Progression;

/**
 * Tiered scalable stat provider.
 * @author DAM
 */
public class TieredScalableStatProvider extends AbstractStatProvider
{
  private List<Progression> _progressions;

  /**
   * Constructor.
   * @param stat Targeted stat.
   * @param nbTiers Number of tiers.
   */
  public TieredScalableStatProvider(STAT stat, int nbTiers)
  {
    super(stat);
    _progressions=new ArrayList<Progression>(nbTiers);
    for(int i=0;i<nbTiers;i++)
    {
      _progressions.add(null);
    }
  }

  /**
   * Get the progression for the given tier.
   * @param tier Targeted tier, starting at 1.
   * @return A progression.
   */
  public Progression getProgression(int tier)
  {
    Progression progression=_progressions.get(tier-1);
    return progression;
  }

  /**
   * Set the progression for the given tier.
   * @param tier Targeted tier, starting at 1.
   * @param progression Progression to set.
   */
  public void setProgression(int tier, Progression progression)
  {
    _progressions.set(tier-1,progression);
  }

  /**
   * Get the number of tiers.
   * @return the number of tiers.
   */
  public int getNumberOfTiers()
  {
    return _progressions.size();
  }

  @Override
  public Float getStatValue(int tier, int level)
  {
    int index=tier-1;
    if (index>=_progressions.size())
    {
      index=_progressions.size()-1;
    }
    Progression progression=_progressions.get(index);
    Float value=progression.getValue(level);
    return value;
  }
}
