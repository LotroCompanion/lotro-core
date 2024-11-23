package delta.games.lotro.character.virtues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.utils.maths.Progression;

/**
 * Description of a virtue.
 * @author DAM
 */
public class VirtueDescription extends TraitDescription
{
  private PropertyModificationEffect _passives;
  private Progression _maxRankForCharacterLevel;
  private String _rankStatKey;
  private String _xpPropertyName;
  private Map<Integer,Integer> _xpForTiers;

  /**
   * Constructor.
   */
  public VirtueDescription()
  {
    _maxRankForCharacterLevel=null;
    _rankStatKey=null;
    _xpPropertyName=null;
    _xpForTiers=new HashMap<Integer,Integer>();
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
   * Get the passives effect.
   * @return An effect.
   */
  public PropertyModificationEffect getPassivesEffect()
  {
    return _passives;
  }

  /**
   * Set the passives effect.
   * @param passives Effect to set.
   */
  public void setPassivesEffect(PropertyModificationEffect passives)
  {
    _passives=passives;
  }

  /**
   * Get the stats provider for passives.
   * @return the stats provider for passives.
   */
  public StatsProvider getPassiveStatsProvider()
  {
    return (_passives!=null)?_passives.getStatsProvider():null;
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

  /**
   * Get all available tiers.
   * @return a sorted list of tiers.
   */
  public List<Integer> getTiers()
  {
    List<Integer> tiers=new ArrayList<Integer>(_xpForTiers.keySet());
    Collections.sort(tiers);
    return tiers;
  }

  /**
   * Set the XP threshold for the given tier.
   * @param tier Tier to use.
   * @return A XP threshold or <code>null</code> if tier is not supported.
   */
  public Integer getXpForTier(int tier)
  {
    return _xpForTiers.get(Integer.valueOf(tier));
  }

  /**
   * Set the XP threshold for the given tier.
   * @param tier Tier to use.
   * @param xp XP threshold to use.
   */
  public void setXpForTier(int tier, int xp)
  {
    _xpForTiers.put(Integer.valueOf(tier),Integer.valueOf(xp));
  }

  /**
   * Get the tier for the given XP amount.
   * @param xp XP amount.
   * @return A tier.
   */
  public int getTierForXp(int xp)
  {
    int ret=0;
    List<Integer> tiers=getTiers();
    for(Integer tier : tiers)
    {
      int xpThreshold=getXpForTier(tier.intValue()).intValue();
      if (xp>=xpThreshold)
      {
       ret=tier.intValue();
      }
    }
    return ret;
  }
}
