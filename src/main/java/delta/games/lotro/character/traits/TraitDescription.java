package delta.games.lotro.character.traits;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Trait.
 * @author DAM
 */
public class TraitDescription implements Identifiable
{
  /**
   * Trait identifier.
   */
  private int _identifier;
  /**
   * Trait/buff internal key (for compatibility with versions<=9.0).
   */
  private String _key;
  /**
   * Trait name (never <code>null</code>).
   */
  private String _name;
  /**
   * Trait description (never <code>null</code>).
   */
  private String _description;
  /**
   * Trait icon identifier.
   */
  private int _iconId;
  /**
   * Minimum level.
   */
  private int _minLevel;
  /**
   * Tiers.
   */
  private int _tiers;
  /**
   * Tier property.
   */
  private String _tierPropertyName;
  /**
   * Stats.
   */
  private StatsProvider _stats;
  // Category (enum?)
  // Nature (enum?)
  // Priority
  // Tooltip
  // Cost to slot
  // Cosmetic (boolean?)
  private List<SkillDescription> _skills;

  /**
   * Constructor.
   */
  public TraitDescription()
  {
    super();
    _identifier=0;
    _key="";
    _name="";
    _description="";
    _iconId=0;
    _minLevel=1;
    _tiers=1;
    _stats=new StatsProvider();
    _skills=new ArrayList<SkillDescription>();
  }

  /**
   * Get the identifier of this trait.
   * @return a trait identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this trait.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the key of this trait.
   * @return a trait key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the key of this trait.
   * @param key the key to set.
   */
  public void setKey(String key)
  {
    _key=key;
  }

  /**
   * Get the name of this trait.
   * @return a trait name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this trait.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the description of this trait.
   * @return a trait description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this trait.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    if (description==null) description="";
    _description=description;
  }

  /**
   * Get the icon ID for this trait.
   * @return an icon ID.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID for this trait.
   * @param iconId Icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the minimum level for this trait.
   * @return a level.
   */
  public int getMinLevel()
  {
    return _minLevel;
  }

  /**
   * Set the minimum level for this trait.
   * @param minLevel the value to set.
   */
  public void setMinLevel(int minLevel)
  {
    _minLevel=minLevel;
  }

  /**
   * Get the number of tiers for this trait.
   * @return a number of tiers.
   */
  public int getTiersCount()
  {
    return _tiers;
  }

  /**
   * Set the number of tiers for this trait.
   * @param tiers the value to set.
   */
  public void setTiersCount(int tiers)
  {
    _tiers=tiers;
  }

  /**
   * Set the tier property name.
   * @param tierPropertyName Tier property name.
   */
  public void setTierPropertyName(String tierPropertyName)
  {
    _tierPropertyName=tierPropertyName;
  }

  /**
   * Get the property name for the tier of this trait.
   * @return A property name or <code>null</code>.
   */
  public String getTierPropertyName()
  {
    return _tierPropertyName;
  }

  /**
   * Get the stats provider.
   * @return the stats provider.
   */
  public StatsProvider getStatsProvider()
  {
    return _stats;
  }

  /**
   * Set the stats provider.
   * @param stats Stats to set.
   */
  public void setStatsProvider(StatsProvider stats)
  {
    _stats=stats;
  }

  /**
   * Add a skill.
   * @param skill Skill to add.
   */
  public void addSkill(SkillDescription skill)
  {
    _skills.add(skill);
  }

  /**
   * Get all the skills of this trait.
   * @return A list of skills.
   */
  public List<SkillDescription> getSkills()
  {
    return _skills;
  }

  @Override
  public String toString()
  {
    return "Trait: "+_name+" ("+_identifier+")";
  }
}
