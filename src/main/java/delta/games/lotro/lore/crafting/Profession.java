package delta.games.lotro.lore.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.Identifiable;

/**
 * Profession.
 * @author DAM
 */
public class Profession implements Identifiable
{
  private int _id;
  private String _key;
  private String _name;
  private String _description;
  private boolean _hasGuild;
  private HashMap<Integer,CraftingLevel> _levels;

  /**
   * Constructor.
   */
  public Profession()
  {
    _id=0;
    _key=null;
    _name="";
    _hasGuild=false;
    _levels=new HashMap<Integer,CraftingLevel>();
  }

  /**
   * Get the identifier of this object.
   * @return an object identifier.
   */
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Set the identifier of this object.
   * @param id Identifier to set.
   */
  public void setIdentifier(int id)
  {
    _id=id;
  }

  /**
   * Get a identifying key for this profession.
   * @return A key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set a identifying key for this profession.
   * @param key Key to set.
   */
  public void setKey(String key)
  {
    _key=key;
  }

  /**
   * Get the displayable name of this profession.
   * @return A displayable name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this profession.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the description of this profession.
   * @return A description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this profession.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    if (description==null)
    {
      description="";
    }
    _description=description;
  }

  /**
   * Indicates if this profession has a guild or not.
   * @return <code>true</code> if it has a guild, <code>false</code> otherwise.
   */
  public boolean hasGuild()
  {
    return _hasGuild;
  }

  /**
   * Indicate if this profession has a guild or not.
   * @param hasGuild <code>true</code> if it does, <code>false</code> otherwise.
   */
  public void setHasGuild(boolean hasGuild)
  {
    _hasGuild=hasGuild;
  }

  /**
   * Add a crafting level.
   * @param level Level to add.
   */
  public void addLevel(CraftingLevel level)
  {
    Integer key=Integer.valueOf(level.getTier());
    _levels.put(key,level);
  }

  /**
   * Get a crafting level by tier.
   * @param tier Tier of the crafting level to get.
   * @return A crafting level instance or <code>null</code> if <code>tier</code> is not known.
   */
  public CraftingLevel getByTier(int tier)
  {
    CraftingLevel level=_levels.get(Integer.valueOf(tier));
    return level;
  }

  /**
   * Get all known crafting levels for this profession.
   * @return a list of crafting levels, ordered by tier.
   */
  public List<CraftingLevel> getLevels()
  {
    List<Integer> keys=new ArrayList<Integer>(_levels.keySet());
    Collections.sort(keys);
    List<CraftingLevel> ret=new ArrayList<CraftingLevel>();
    for(Integer tier : keys)
    {
      ret.add(_levels.get(tier));
    }
    return ret;
  }

  /**
   * Get the maximum level.
   * @return the maximum level.
   */
  public CraftingLevel getMaximumLevel()
  {
    return _levels.get(Integer.valueOf(_levels.size()-1));
  }

  /**
   * Get the beginner level.
   * @return the beginner level.
   */
  public CraftingLevel getBeginnerLevel()
  {
    return _levels.get(Integer.valueOf(0));
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
