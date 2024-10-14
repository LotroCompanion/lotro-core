package delta.games.lotro.character.skills;

import java.util.Set;

import delta.games.lotro.character.skills.attack.SkillAttacks;
import delta.games.lotro.character.skills.geometry.SkillGeometry;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillDisplayType;
import delta.games.lotro.common.inductions.Induction;
import delta.games.lotro.common.properties.ModPropertyList;

/**
 * Skill details.
 * @author DAM
 */
public class SkillDetails implements Identifiable,Named
{
  private int _id;
  private String _name;
  // Duration, induction, channeling
  private Float _actionDurationContribution;
  private Induction _induction;
  private Float _channelingDuration;
  // Cooldown
  private Float _cooldown;
  private ModPropertyList _cooldownMods;
  // Flags
  private int _flags;
  // Geometry
  private SkillGeometry _geometry;
  // Max targets
  private Integer _maxTargets;
  private ModPropertyList _maxTargetsMods;
  // Resist category
  private ResistCategory _resistCategory;
  // Display types
  private Set<SkillDisplayType> _displayTypes;
  // Effects
  private SkillEffectsManager _effects;
  // Morale/power cost
  private SkillCostData _cost;
  // PIP (optional)
  private SkillPipData _pipData;
  // Gambit (optional)
  private SkillGambitData _gambitData;
  // Attacks
  private SkillAttacks _attacks;

  /**
   * Constructor.
   */
  public SkillDetails()
  {
    _actionDurationContribution=null;
    _induction=null;
    _channelingDuration=null;
    _cooldown=null;
    _flags=0;
    _geometry=null;
    _maxTargets=null;
    _resistCategory=null;
    _displayTypes=null;
    _cost=null;
    _pipData=null;
    _gambitData=null;
    _attacks=null;
  }

  /**
   * Get the skill identifier;
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Set the skill identifier.
   * @param id the identifier to set.
   */
  public void setId(int id)
  {
    _id=id;
  }

  /**
   * Get the skill name.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the skill name.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the action duration contribution.
   * @return a duration or <code>null</code>.
   */
  public Float getActionDurationContribution()
  {
    return _actionDurationContribution;
  }

  /**
   * Set the action duration contribution.
   * @param actionDurationContribution Duration to set (may be <code>null</code>).
   */
  public void setActionDurationContribution(Float actionDurationContribution)
  {
    _actionDurationContribution=actionDurationContribution;
  }

  /**
   * Get the induction.
   * @return An induction or <code>null</code>.
   */
  public Induction getInduction()
  {
    return _induction;
  }

  /**
   * Set the induction.
   * @param induction Induction to set (may be <code>null</code>).
   */
  public void setInduction(Induction induction)
  {
    _induction=induction;
  }

  /**
   * Get the channeling duration.
   * @return a duration or <code>null</code>.
   */
  public Float getChannelingDuration()
  {
    return _channelingDuration;
  }

  /**
   * Set the channeling duration.
   * @param channelingDuration Duration to set (may be <code>null</code>).
   */
  public void setChannelingDuration(Float channelingDuration)
  {
    _channelingDuration=channelingDuration;
  }

  /**
   * Get the skill cooldown.
   * @return A cooldown (seconds) or <code>null</code>.
   */
  public Float getCooldown()
  {
    return _cooldown;
  }

  /**
   * Set the cooldown.
   * @param cooldown Cooldown to set (may be <code>null</code>).
   */
  public void setCooldown(Float cooldown)
  {
    _cooldown=cooldown;
  }

  /**
   * Get the skill cooldown modifiers.
   * @return Some modifiers or <code>null</code>.
   */
  public ModPropertyList getCooldownMods()
  {
    return _cooldownMods;
  }

  /**
   * Set the cooldown modifiers.
   * @param cooldownMods Modifiers to set (may be <code>null</code>).
   */
  public void setCooldownMods(ModPropertyList cooldownMods)
  {
    _cooldownMods=cooldownMods;
  }

  /**
   * Get the raw flags value.
   * @return flags.
   */
  public int getFlags()
  {
    return _flags;
  }

  /**
   * Set the raw flags value.
   * @param flags Flags to set.
   */
  public void setFlags(int flags)
  {
    _flags=flags;
  }

  /**
   * Get a flag value.
   * @param flag Flag to test.
   * @return <code>true</code> if set, <code>false</code> otherwise.
   */
  public boolean getFlag(int flag)
  {
    return ((_flags&flag)!=0);
  }

  /**
   * Set a flag value.
   * @param flag Flag to set (from SkillAttackFlags).
   * @param set Set it or unset it.
   */
  public void setFlag(int flag, boolean set)
  {
    if (set)
    {
      _flags=(_flags|flag);
    }
    else
    {
      _flags=(_flags&(~flag));
    }
  }

  /**
   * Get the skill geometry.
   * @return a geometry.
   */
  public SkillGeometry getGeometry()
  {
    return _geometry;
  }

  /**
   * Set the skill geometry.
   * @param geometry Geometry to set.
   */
  public void setGeometry(SkillGeometry geometry)
  {
    _geometry=geometry;
  }

  /**
   * Get the maximum number of targets.
   * @return A targets count or <code>null</code>.
   */
  public Integer getMaxTargets()
  {
    return _maxTargets;
  }

  /**
   * Set the maximum targets count.
   * @param maxTargets Value to set.
   */
  public void setMaxTargets(Integer maxTargets)
  {
    _maxTargets=maxTargets;
  }

  /**
   * @return the maxTargetsMods
   */
  public ModPropertyList getMaxTargetsMods()
  {
    return _maxTargetsMods;
  }

  /**
   * @param maxTargetsMods the maxTargetsMods to set
   */
  public void setMaxTargetsMods(ModPropertyList maxTargetsMods)
  {
    _maxTargetsMods=maxTargetsMods;
  }

  /**
   * Get the resist category.
   * @return a resist category.
   */
  public ResistCategory getResistCategory()
  {
    return _resistCategory;
  }

  /**
   * Set the resist category.
   * @param resistCategory Category to set.
   */
  public void setResistCategory(ResistCategory resistCategory)
  {
    _resistCategory=resistCategory;
  }

  /**
   * Get the display types.
   * @return some display types or <code>null</code>.
   */
  public Set<SkillDisplayType> getDisplayTypes()
  {
    return _displayTypes;
  }

  /**
   * Set the display types.
   * @param displayTypes Types to set (may be <code>null</code>).
   */
  public void setDisplayTypes(Set<SkillDisplayType> displayTypes)
  {
    _displayTypes=displayTypes;
  }

  /**
   * Get the effects manager. 
   * @return An effects manager or <code>null</code> if no effects.
   */
  public SkillEffectsManager getEffects()
  {
    return _effects;
  }

  /**
   * Set the effects manager.
   * @param effects Effects manager to set (may be <code>null</code).
   */
  public void setEffects(SkillEffectsManager effects)
  {
    _effects=effects;
  }

  /**
   * Get the skill cost data.
   * @return skill cost data.
   */
  public SkillCostData getCostData()
  {
    return _cost;
  }

  /**
   * Set the skill cost data.
   * @param cost Data to set.
   */
  public void setCostData(SkillCostData cost)
  {
    _cost=cost;
  }

  /**
   * Set the PIP data.
   * @return PIP data or <code>null</code>.
   */
  public SkillPipData getPIPData()
  {
    return _pipData;
  }

  /**
   * Set the PIP data.
   * @param pipData Data to set.
   */
  public void setPIPData(SkillPipData pipData)
  {
    _pipData=pipData;
  }

  /**
   * Get the gambit data.
   * @return Gambit data or <code>null</code>
   */
  public SkillGambitData getGambitData()
  {
    return _gambitData;
  }

  /**
   * Set the gambit data.
   * @param gambitData Data to set.
   */
  public void setGambitData(SkillGambitData gambitData)
  {
    _gambitData=gambitData;
  }

  /**
   * Get the attacks.
   * @return attacks data.
   */
  public SkillAttacks getAttacks()
  {
    return _attacks;
  }

  /**
   * Set the attacks data.
   * @param attacks Data to set.
   */
  public void setAttacks(SkillAttacks attacks)
  {
    _attacks=attacks;
  }
}
