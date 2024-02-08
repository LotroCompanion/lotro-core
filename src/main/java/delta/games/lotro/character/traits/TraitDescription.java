package delta.games.lotro.character.traits;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.traits.prerequisites.AbstractTraitPrerequisite;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.enums.SkillCategory;
import delta.games.lotro.common.enums.TraitNature;
import delta.games.lotro.common.enums.TraitSubCategory;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.utils.maths.ArrayProgression;

/**
 * Trait.
 * @author DAM
 */
public class TraitDescription implements Identifiable,Named
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
   * Overlay icon ID.
   */
  private Integer _staticIconOverlayId;
  /**
   * Rank overlay progression.
   */
  private ArrayProgression _rankOverlayProgression;
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
  /**
   * Category.
   */
  private SkillCategory _category;
  /**
   * Nature.
   */
  private TraitNature _traitNature;
  /**
   * Trait sub-category.
   */
  private TraitSubCategory _subCategory;
  // Priority: int
  /**
   * Tooltip.
   */
  private String _tooltip;
  // Cost to slot: int (cost in coppers?)
  /**
   * Cosmetic.
   */
  private boolean _cosmetic;
  /**
   * Skills.
   */
  private List<SkillDescription> _skills;
  /**
   * Effects.
   */
  private List<EffectAtRank> _effects;
  private List<EffectGenerator> _effectGenerators;
  /**
   * Pre-requisites.
   */
  private AbstractTraitPrerequisite _prerequisites;

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
    _category=null;
    _traitNature=null;
    _subCategory=null;
    _tooltip="";
    _cosmetic=false;
    _skills=new ArrayList<SkillDescription>();
    _effects=new ArrayList<EffectAtRank>();
    _effectGenerators=new ArrayList<EffectGenerator>();
    _prerequisites=null;
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
   * Get the static icon overlay ID.
   * @return An ID or <code>null</code> if none.
   */
  public Integer getStaticIconOverlayId()
  {
    return _staticIconOverlayId;
  }

  /**
   * Set the static icon overlay ID.
   * @param staticIconOverlayId ID to set (may be <code>null</code>).
   */
  public void setStaticIconOverlayId(Integer staticIconOverlayId)
  {
    _staticIconOverlayId=staticIconOverlayId;
  }

  /**
   * Get the rank overlay progression.
   * @return A progression for rank overlay (may be <code>null</code>).
   */
  public ArrayProgression getRankOverlayProgression()
  {
    return _rankOverlayProgression;
  }

  /**
   * Set the rank overlay progression.
   * @param rankOverlayProgression Progression to set (may be <code>null</code>).
   */
  public void setRankOverlayProgression(ArrayProgression rankOverlayProgression)
  {
    _rankOverlayProgression=rankOverlayProgression;
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
   * Get the category.
   * @return a category.
   */
  public SkillCategory getCategory()
  {
    return _category;
  }

  /**
   * Set the category.
   * @param category Category to set.
   */
  public void setCategory(SkillCategory category)
  {
    _category=category;
  }

  /**
   * Get the trait nature.
   * @return a trait nature.
   */
  public TraitNature getNature()
  {
    return _traitNature;
  }

  /**
   * Set the trait nature.
   * @param traitNature Trait nature to set.
   */
  public void setNature(TraitNature traitNature)
  {
    _traitNature=traitNature;
  }

  /**
   * Get the sub-category.
   * @return a sub-category or <code>null</code>.
   */
  public TraitSubCategory getSubCategory()
  {
    return _subCategory;
  }

  /**
   * Set the sub-category.
   * @param subCategory Sub-category to set.
   */
  public void setSubCategory(TraitSubCategory subCategory)
  {
    _subCategory=subCategory;
  }

  /**
   * Get the tooltip.
   * @return a tooltip.
   */
  public String getTooltip()
  {
    return _tooltip;
  }

  /**
   * Set the tooltip.
   * @param tooltip Tooltip to set.
   */
  public void setTooltip(String tooltip)
  {
    if (tooltip==null)
    {
      tooltip="";
    }
    _tooltip=tooltip;
  }

  /**
   * Indicates if this trait is cosmetic.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isCosmetic()
  {
    return _cosmetic;
  }

  /**
   * Set the 'cosmetic' flag.
   * @param cosmetic Value to set.
   */
  public void setCosmetic(boolean cosmetic)
  {
    _cosmetic=cosmetic;
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

  /**
   * Add an effect.
   * @param effect Effect to add.
   * @param rank Rank.
   */
  public void addEffect(Effect effect, int rank)
  {
    _effects.add(new EffectAtRank(effect,rank));
  }

  /**
   * Get all the effects of this trait.
   * @return A list of effects.
   */
  public List<EffectAtRank> getEffects()
  {
    return _effects;
  }

  /**
   * Add an effect generator.
   * @param effect Effect to add.
   */
  public void addEffectGenerator(EffectGenerator effect)
  {
    _effectGenerators.add(effect);
  }

  /**
   * Get all the effect generators of this trait.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getEffectGenerators()
  {
    return _effectGenerators;
  }

  /**
   * Get the trait prerequisites.
   * @return A trait pre-requisites or <code>null</code>.
   */
  public AbstractTraitPrerequisite getTraitPrerequisites()
  {
    return _prerequisites;
  }

  /**
   * Set the trait pre-requisites.
   * @param prerequisites Pre-requisites to set.
   */
  public void setTraitPrerequisite(AbstractTraitPrerequisite prerequisites)
  {
    _prerequisites=prerequisites;
  }

  @Override
  public String toString()
  {
    return "Trait: "+_name+" ("+_identifier+")";
  }
}
