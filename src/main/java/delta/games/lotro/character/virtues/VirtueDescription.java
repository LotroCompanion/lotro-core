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
  private String _rankStatKey;
  private String _xpPropertyName;

  /**
   * Constructor.
   */
  public VirtueDescription()
  {
    _passives=new StatsProvider();
    _maxRankForCharacterLevel=null;
    _rankStatKey=null;
    _xpPropertyName=null;
  }

  /**
   * Get the persistence key for this virtue.
   * @return a persistence key.
   */
  public String getPersistenceKey()
  {
    String key=getKey();
    if (key==null)
    {
      key=String.valueOf(getIdentifier());
    }
    return key;
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
   * @return A progression.
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

  /**
   * Get the max rank for this virtue at the given character level.
   * @param characterLevel Character level.
   * @return a rank value.
   */
  public int getMaxRank(int characterLevel)
  {
    Float maxRank=_maxRankForCharacterLevel.getValue(characterLevel);
    return (maxRank!=null)?maxRank.intValue():0;
  }

  /**
   * Get the key of the stat used for virtue rank.
   * @return a key.
   */
  public String getRankStatKey()
  {
    return _rankStatKey;
  }

  /**
   * Set the key of the stat used for virtue rank.
   * @param rankStatKey A key.
   */
  public void setRankStatKey(String rankStatKey)
  {
    _rankStatKey=rankStatKey;
  }

  /**
   * Get the property name for the XP property.
   * @return a property name.
   */
  public String getXpPropertyName()
  {
    return _xpPropertyName;
  }

  /**
   * Set the property name for the XP property.
   * @param xpPropertyName Property name to set.
   */
  public void setXpPropertyName(String xpPropertyName)
  {
    _xpPropertyName=xpPropertyName;
  }
}
