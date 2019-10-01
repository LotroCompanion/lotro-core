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
  private int _lowestTier;
  private int _initialTier;
  private int _highestTier;
  private FactionLevel _initialLevel;
  private List<FactionLevel> _levels;

  /**
   * Constructor.
   */
  public Faction()
  {
    _identifier=0;
    _key=null;
    _name="";
    _description="";
    _category="";
    _levels=new ArrayList<FactionLevel>();
    _initialLevel=null;
  }

  /**
   * Constructor.
   * @param key Identifying key.
   * @param name Name of faction.
   * @param category Category.
   * @param levels Available levels.
   */
  public Faction(String key, String name, String category, List<FactionLevel> levels)
  {
    _identifier=0;
    _key=key;
    _name=name;
    _description="";
    _category=category;
    _levels=new ArrayList<FactionLevel>(levels);
    _initialLevel=levels.get(0);
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
   * Set the identifier of this faction.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the identifying key for this faction.
   * @return An identifying key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the identifying key for this faction.
   * @param key Key to set.
   */
  public void setKey(String key)
  {
    _key=key;
  }

  /**
   * Get an identifying key for this faction.
   * @return an identifying key.
   */
  public String getIdentifyingKey()
  {
    if (_key!=null) return _key;
    return String.valueOf(_identifier);
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
   * Get the initial reputation level in this faction.
   * @return a faction level;
   */
  public FactionLevel getInitialLevel()
  {
    return _initialLevel;
  }

  /**
   * Set the initial level.
   * @param level Level to set.
   */
  public void setInitialLevel(FactionLevel level)
  {
    _initialLevel=level;
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
   * Get a level of this faction using its key.
   * @param levelKey Key of the level to get.
   * @return A level or <code>null</code> if not found.
   */
  public FactionLevel getLevelByKey(String levelKey)
  {
    if (levelKey==null)
    {
      return null;
    }
    for(FactionLevel level : _levels)
    {
      if (levelKey.equals(level.getKey()))
      {
        return level;
      }
    }
    return null;
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

  @Override
  public String toString()
  {
    return _name+" ("+_identifier+")";
  }
}
