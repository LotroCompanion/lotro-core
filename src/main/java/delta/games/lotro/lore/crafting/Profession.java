package delta.games.lotro.lore.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.lore.reputation.Faction;

/**
 * Profession.
 * @author DAM
 */
public class Profession implements Identifiable,Named
{
  private int _id;
  private String _key;
  private String _name;
  private String _description;
  private Faction _guildFaction;
  private HashMap<Integer,CraftingLevel> _levels;
  private String _enabledPropertyName;
  private String _masteryLevelPropertyName;
  private String _masteryXpPropertyName;
  private String _proficiencyLevelPropertyName;
  private String _proficiencyXpPropertyName;
  private String _extraRecipesPropertyName;

  /**
   * Constructor.
   */
  public Profession()
  {
    _id=0;
    _key=null;
    _name="";
    _guildFaction=null;
    _levels=new HashMap<Integer,CraftingLevel>();
    _enabledPropertyName="";
    _masteryLevelPropertyName="";
    _masteryXpPropertyName="";
    _proficiencyLevelPropertyName="";
    _proficiencyXpPropertyName="";
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
    return _guildFaction!=null;
  }

  /**
   * Get the associated guild faction.
   * @return A faction or <code>null</code> if no associated guild.
   */
  public Faction getGuildFaction()
  {
    return _guildFaction;
  }

  /**
   * Set the associated guild faction, if any.
   * @param guildFaction Associated guild faction.
   */
  public void setGuildFaction(Faction guildFaction)
  {
    _guildFaction=guildFaction;
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

  /**
   * Get the 'enabled' property name.
   * @return a property name.
   */
  public String getEnabledPropertyName()
  {
    return _enabledPropertyName;
  }

  /**
   * Get the 'mastery level' property name.
   * @return a property name.
   */
  public String getMasteryLevelPropertyName()
  {
    return _masteryLevelPropertyName;
  }

  /**
   * Get the 'mastery XP' property name.
   * @return a property name.
   */
  public String getMasteryXpPropertyName()
  {
    return _masteryXpPropertyName;
  }

  /**
   * Get the 'proficiency level' property name.
   * @return a property name.
   */
  public String getProficiencyLevelPropertyName()
  {
    return _proficiencyLevelPropertyName;
  }

  /**
   * Get the 'proficiency XP' property name.
   * @return a property name.
   */
  public String getProficiencyXpPropertyName()
  {
    return _proficiencyXpPropertyName;
  }

  /**
   * Get the 'extra recipes' property name.
   * @return a property name.
   */
  public String getExtraRecipesPropertyName()
  {
    return _extraRecipesPropertyName;
  }

  /**
   * Set the property names.
   * @param enabled 'Enabled' property name.
   * @param masteryLevel 'Mastery level' property name.
   * @param masteryXp 'Mastery XP' property name.
   * @param proficiencyLevel 'Proficiency level' property name.
   * @param proficiencyXp 'Proficiency XP' property name.
   * @param extraRecipes 'Extra recipes' property name.
   */
  public void setPropertyNames(String enabled, String masteryLevel, String masteryXp,
      String proficiencyLevel, String proficiencyXp,
      String extraRecipes)
  {
    _enabledPropertyName=enabled;
    _masteryLevelPropertyName=masteryLevel;
    _masteryXpPropertyName=masteryXp;
    _proficiencyLevelPropertyName=proficiencyLevel;
    _proficiencyXpPropertyName=proficiencyXp;
    _extraRecipesPropertyName=extraRecipes;
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
