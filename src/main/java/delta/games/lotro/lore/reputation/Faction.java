package delta.games.lotro.lore.reputation;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;

/**
 * Represents a faction in the LOTRO world.
 * @author DAM
 */
public class Faction implements Identifiable
{
  private int _identifier;
  private String _key;
  private String _name;
  private String _description;
  private String _category;
  private boolean _isGuildFaction;
  private int _lowestTier;
  private int _initialTier;
  private int _highestTier;
  private List<FactionLevel> _levels;
  private String _currentTierPropertyName;
  private String _currentReputationPropertyName;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public Faction(int identifier)
  {
    _identifier=identifier;
    _key=null;
    _name="";
    _description="";
    _category="";
    _isGuildFaction=false;
    _levels=new ArrayList<FactionLevel>();
    _currentTierPropertyName=null;
    _currentReputationPropertyName=null;
  }

  /**
   * Get the identifier of this faction.
   * @return the identifier of this faction.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get an identifying key for this faction.
   * @return an identifying key.
   */
  public String getIdentifyingKey()
  {
    return String.valueOf(_identifier);
  }

  /**
   * Get the legacy key, if any.
   * @return A string key or <code>null</code>.
   */
  public String getLegacyKey()
  {
    return _key;
  }

  /**
   * Set the legacy key.
   * @param key Legacy key to set.
   */
  public void setLegacyKey(String key)
  {
    _key=key;
  }

  /**
   * Get the name of this faction.
   * @return the name of this faction.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this faction.
   * @param name Faction name.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the description of this faction.
   * @return the description of this faction.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this faction.
   * @param description A description.
   */
  public void setDescription(String description)
  {
    _description=description;
  }

  /**
   * Get the category of this faction.
   * @return the category of this faction.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category of this faction.
   * @param category Category to set.
   */
  public void setCategory(String category)
  {
    _category=category;
  }

  /**
   * Indicates if this is a guild faction or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isGuildFaction()
  {
    return _isGuildFaction;
  }

  /**
   * Set the 'guild faction' flag.
   * @param isGuildFaction Value to set.
   */
  public void setIsGuildFaction(boolean isGuildFaction)
  {
    _isGuildFaction=isGuildFaction;
  }

  /**
   * Get the lowest tier.
   * @return the lowest tier.
   */
  public int getLowestTier()
  {
    return _lowestTier;
  }

  /**
   * Set the lowest tier.
   * @param lowestTier Lowest tier.
   */
  public void setLowestTier(int lowestTier)
  {
    _lowestTier=lowestTier;
  }

  /**
   * Get the initial tier.
   * @return the initial tier.
   */
  public int getInitialTier()
  {
    return _initialTier;
  }

  /**
   * Set the initial tier.
   * @param initialTier Initial tier.
   */
  public void setInitialTier(int initialTier)
  {
    _initialTier=initialTier;
  }

  /**
   * Get the highest tier.
   * @return the highest tier.
   */
  public int getHighestTier()
  {
    return _highestTier;
  }

  /**
   * Set the highest tier.
   * @param highestTier Highest tier.
   */
  public void setHighestTier(int highestTier)
  {
    _highestTier=highestTier;
  }

  /**
   * Get the next level compared to the given one.
   * @param level Input level.
   * @return A level or <code>null</code> if none.
   */
  public FactionLevel getPrevious(FactionLevel level)
  {
    int index=_levels.indexOf(level);
    if (index==-1)
    {
      return null;
    }
    if (index==0)
    {
      return null;
    }
    return _levels.get(index-1);
  }

  /**
   * Get the next level compared to the given one.
   * @param level Input level.
   * @return A level or <code>null</code> if none.
   */
  public FactionLevel getNext(FactionLevel level)
  {
    int index=_levels.indexOf(level);
    if (index==-1)
    {
      return null;
    }
    if (index==_levels.size()-1)
    {
      return null;
    }
    return _levels.get(index+1);
  }

  /**
   * Add a faction level.
   * @param level Level to add.
   */
  public void addFactionLevel(FactionLevel level)
  {
    _levels.add(level);
  }

  /**
   * Get the ordered levels for this faction.
   * @return an array of levels.
   */
  public FactionLevel[] getLevels()
  {
    FactionLevel[] ret=new FactionLevel[_levels.size()];
    ret=_levels.toArray(ret);
    return ret;
  }

  /**
   * Get a level of this faction using its tier.
   * @param tier Tier of the level to get.
   * @return A level or <code>null</code> if not found.
   */
  public FactionLevel getLevelByTier(int tier)
  {
    for(FactionLevel level : _levels)
    {
      if (level.getTier()==tier)
      {
        return level;
      }
    }
    return null;
  }

  /**
   * Get the property name for the current tier.
   * @return a property name.
   */
  public String getCurrentTierPropertyName()
  {
    return _currentTierPropertyName;
  }

  /**
   * Set the property name for the current tier.
   * @param currentTierPropertyName the property name to set.
   */
  public void setCurrentTierPropertyName(String currentTierPropertyName)
  {
    _currentTierPropertyName=currentTierPropertyName;
  }

  /**
   * Get the property name for the current reputation value.
   * @return a property name.
   */
  public String getCurrentReputationPropertyName()
  {
    return _currentReputationPropertyName;
  }

  /**
   * Set the property name for the current reputation value.
   * @param currentReputationPropertyName the property name to set.
   */
  public void setCurrentReputationPropertyName(String currentReputationPropertyName)
  {
    _currentReputationPropertyName=currentReputationPropertyName;
  }

  @Override
  public String toString()
  {
    return _name+" ("+_identifier+")";
  }
}
