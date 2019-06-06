package delta.games.lotro.character.virtues;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.utils.maths.Progression;

/**
 * Description of a virtue.
 * @author DAM
 */
public class VirtueDescription extends TraitDescription
{
  private StatsProvider _passives;
  private Progression _maxRankForCharacterLevel;

  /**
   * Constructor.
   */
  public VirtueDescription()
  {
    _passives=new StatsProvider();
    _maxRankForCharacterLevel=null;
  }

  /**
   * Get the stats provider for passives.
   * @return the stats provider for passives.
   */
  public StatsProvider getPassiveStatsProvider()
  {
    return _passives;
  }

  /**
   * Set the stats provider for passives.
   * @param stats Provider to set.
   */
  public void setPassiveStatsProvider(StatsProvider stats)
  {
    _passives=stats;
  }

  /**
   * Get the progression that gives the maximum virtue rank for a character level.
   * @return A progression?.
   */
  public Progression getMaxRankForCharacterLevelProgression()
  {
    return _maxRankForCharacterLevel;
  }

  /**
   * Set the progression that gives the maximum virtue rank for a character level.
   * @param progression Progression to set.
   */
  public void setMaxRankForCharacterLevelProgression(Progression progression)
  {
    _maxRankForCharacterLevel=progression;
  }
}
