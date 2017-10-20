package delta.games.lotro.lore.reputation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a faction in the LOTRO world.
 * @author DAM
 */
public class Faction
{
  private String _key;
  private String _name;
  private Set<String> _aliases;
  private String _category;
  private FactionLevel _initialLevel;
  private FactionLevel[] _levels;
  
  /**
   * Constructor.
   * @param key Identifying key.
   * @param name Name of faction.
   * @param category Category.
   * @param levels Available levels.
   */
  public Faction(String key, String name, String category, List<FactionLevel> levels)
  {
    _key=key;
    _name=name;
    _category=category;
    _aliases=new HashSet<String>();
    _levels=levels.toArray(new FactionLevel[levels.size()]);
    _initialLevel=levels.get(0);
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
   * Get the name of this faction.
   * @return the name of this faction.
   */
  public String getName()
  {
    return _name;
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
   * Get the aliases for this faction.
   * @return A possibly empty array of strings.
   */
  public String[] getAliases()
  {
    return _aliases.toArray(new String[_aliases.size()]);
  }

  /**
   * Add an alias to this faction.
   * @param alias Alias to add.
   */
  public void addAlias(String alias)
  {
    _aliases.add(alias);
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
   * Get the ordered levels for this faction.
   * @return an array of levels.
   */
  public FactionLevel[] getLevels()
  {
    return _levels;
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

  @Override
  public String toString()
  {
    return _name;
  }
}
