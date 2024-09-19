package delta.games.lotro.character.skills.attack;

import delta.games.lotro.character.skills.SkillEffectsManager;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.DamageQualifiers;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.DamageTypes;
import delta.games.lotro.utils.maths.Progression;

/**
 * Skill attack description.
 * @author DAM
 */
public class SkillAttack
{
  private DamageQualifier _damageQualifier;
  // Damage type (may be <code>null</code>)
  private DamageType _damageType;
  // DPS
  private Progression _dpsModProgression;
  private ModPropertyList _dpsMods;
  // Max damage
  private float _maxDamage;
  private float _maxDamageVariance;
  private Progression _maxDamageProgression;
  private ModPropertyList _maxDamageMods;
  // Damage modifier
  private float _damageModifier=1.0f;
  private ModPropertyList _damageModifierMods;
  // Multipliers
  private Float _damageContributionMultiplier;
  private Float _implementContributionMultiplier;
  // Flags
  private int _flags;
  // Effects
  private SkillEffectsManager _effects;

  /**
   * Constructor.
   */
  public SkillAttack()
  {
    _damageQualifier=DamageQualifiers.MELEE;
    _dpsMods=null;
    _maxDamageMods=null;
    _damageModifierMods=null;
    _damageType=DamageTypes.COMMON;
    _damageContributionMultiplier=null;
    _dpsModProgression=null;
    _maxDamage=0;
    _maxDamageVariance=0;
    _maxDamageProgression=null;
    _damageModifier=1.0f;
    _implementContributionMultiplier=null;
    _flags=0;
  }

  /**
   * Get the damage qualifier.
   * @return the damage qualifier.
   */
  public DamageQualifier getDamageQualifier()
  {
    return _damageQualifier;
  }

  /**
   * Set the damage qualifier.
   * @param damageQualifier Damage qualifier to set.
   */
  public void setDamageQualifier(DamageQualifier damageQualifier)
  {
    _damageQualifier=damageQualifier;
  }

  /**
   * Get the DPS modifiers.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getDPSMods()
  {
    return _dpsMods;
  }

  /**
   * Set the DPS modifiers.
   * @param dpsMods Modifiers to set (may be <code>null</code>).
   */
  public void setDPSMods(ModPropertyList dpsMods)
  {
    _dpsMods=dpsMods;
  }

  /**
   * Get the max damage modifiers.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getMaxDamageMods()
  {
    return _maxDamageMods;
  }

  /**
   * Set the max damage modifiers.
   * @param maxDamageMods Modifiers to set (may be <code>null</code>).
   */
  public void setMaxDamageMods(ModPropertyList maxDamageMods)
  {
    _maxDamageMods=maxDamageMods;
  }

  /**
   * Get the damage modifiers modifiers.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getDamageModifiersMods()
  {
    return _damageModifierMods;
  }

  /**
   * Set the damage modifiers modifiers.
   * @param damageModifierMods Modifiers to set (may be <code>null</code>).
   */
  public void setDamageModifiersMods(ModPropertyList damageModifierMods)
  {
    _damageModifierMods=damageModifierMods;
  }

  /**
   * Get the damage type.
   * @return the damage type.
   */
  public DamageType getDamageType()
  {
    return _damageType;
  }

  /**
   * Set the damage type.
   * @param damageType Damage type to set.
   */
  public void setDamageType(DamageType damageType)
  {
    _damageType=damageType;
  }

  /**
   * Get the damage contribution multiplier.
   * @return the damage contribution multiplier.
   */
  public Float getDamageContributionMultiplier()
  {
    return _damageContributionMultiplier;
  }

  /**
   * Set the damage contribution multiplier.
   * @param damageContributionMultiplier Multiplier to set.
   */
  public void setDamageContributionMultiplier(Float damageContributionMultiplier)
  {
    _damageContributionMultiplier=damageContributionMultiplier;
  }

  /**
   * Get the DPS modifier progression.
   * @return A progression or <code>null</code>.
   */
  public Progression getDPSModProgression()
  {
    return _dpsModProgression;
  }

  /**
   * Set the DPS modifier progression.
   * @param dpsModProgression Progression to set (may be <code>null</code>).
   */
  public void setDPSModProgression(Progression dpsModProgression)
  {
    _dpsModProgression=dpsModProgression;
  }

  /**
   * Get the maximum damage.
   * @return the maximum damage.
   */
  public float getMaxDamage()
  {
    return _maxDamage;
  }

  /**
   * Set the maximum damage.
   * @param maxDamage Value to set.
   */
  public void setMaxDamage(float maxDamage)
  {
    _maxDamage=maxDamage;
  }

  /**
   * Get the maximum damage variance.
   * @return the maximum damage.
   */
  public float getMaxDamageVariance()
  {
    return _maxDamageVariance;
  }

  /**
   * Set the maximum damage variance.
   * @param maxDamageVariance Value to set.
   */
  public void setMaxDamageVariance(float maxDamageVariance)
  {
    _maxDamageVariance=maxDamageVariance;
  }

  /**
   * Get the maximum damage progression.
   * @return A progression or <code>null</code>.
   */
  public Progression getMaxDamageProgression() 
  {
    return _maxDamageProgression;
  }

  /**
   * Set the maximum damage progression.
   * @param maxDamageProgression Progression to set (may be <code>null</code>).
   */
  public void setMaxDamageProgression(Progression maxDamageProgression)
  {
    _maxDamageProgression=maxDamageProgression;
  }

  /**
   * Get the damage modifier.
   * @return the damage modifier.
   */
  public float getDamageModifier()
  {
    return _damageModifier;
  }

  /**
   * Set the damage modifier.
   * @param damageModifier Value to set.
   */
  public void setDamageModifier(float damageModifier)
  {
    _damageModifier=damageModifier;
  }

  /**
   * Get the implement contribution multiplier.
   * @return the implement contribution multiplier.
   */
  public Float getImplementContributionMultiplier()
  {
    return _implementContributionMultiplier;
  }

  /**
   * Set the implement contribution multiplier.
   * @param implementContributionMultiplier Value to set.
   */
  public void setImplementContributionMultiplier(Float implementContributionMultiplier)
  {
    _implementContributionMultiplier=implementContributionMultiplier;
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
   * Indicates if a flag is set.
   * @param flag Flag to test (from SkillAttackFlags).
   * @return <code>true</code> if it is set, <code>false</code> otherwise.
   */
  public boolean isSet(int flag)
  {
    return ((_flags&flag)==flag);
  }

  /**
   * Get the implement usage type for this attack.
   * @return an implement usage type or <code>null</code>.
   */
  public ImplementUsageType getImplementUsageType()
  {
    if (isSet(SkillAttackFlags.PRIMARY)) return ImplementUsageTypes.PRIMARY;
    if (isSet(SkillAttackFlags.SECONDARY)) return ImplementUsageTypes.SECONDARY;
    if (isSet(SkillAttackFlags.RANGED)) return ImplementUsageTypes.RANGED;
    if (isSet(SkillAttackFlags.TACTICAL)) return ImplementUsageTypes.TACTICAL_DPS;
    if (isSet(SkillAttackFlags.NATURAL)) return ImplementUsageTypes.NATURAL;
    return null;
  }

  /**
   * Get the effects associated with this attack.
   * @return An effects manager or <code>null</code>.
   */
  public SkillEffectsManager getEffects()
  {
    return _effects;
  }

  /**
   * Set the effects associated with this attack.
   * @param effects Effects manager to set (may be <code>null</code>).
   */
  public void setEffects(SkillEffectsManager effects)
  {
    _effects=effects;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();

    sb.append("Qualifier: ").append(_damageQualifier);
    if (_dpsMods!=null)
    {
      sb.append(", DPS mods=").append(_dpsMods);
    }
    if (_maxDamageMods!=null)
    {
      sb.append(", Max damage mods=").append(_maxDamageMods);
    }
    if (_damageModifierMods!=null)
    {
      sb.append(", damage modifiers mods=").append(_damageModifierMods);
    }
    sb.append(", type=").append(_damageType);
    if (_damageContributionMultiplier!=null)
    {
      sb.append(", damage contrib x=").append(_damageContributionMultiplier);
    }
    if (_dpsModProgression!=null)
    {
      sb.append(", DPS mod progression=").append(_dpsModProgression);
    }
    sb.append(", max damage=").append(_maxDamage);
    sb.append(", max damage variance=").append(_maxDamageVariance);
    if (_maxDamageProgression!=null)
    {
      sb.append(", max damage progression=").append(_maxDamageProgression);
    }
    sb.append(", damage modifier=").append(_damageModifier);
    if (_implementContributionMultiplier!=null)
    {
      sb.append(", implement contrib x=").append(_implementContributionMultiplier);
    }
    if (isSet(SkillAttackFlags.NATURAL)) sb.append(", natural");
    if (isSet(SkillAttackFlags.PRIMARY)) sb.append(", primary");
    if (isSet(SkillAttackFlags.RANGED)) sb.append(", ranged");
    if (isSet(SkillAttackFlags.SECONDARY)) sb.append(", secondary");
    if (isSet(SkillAttackFlags.TACTICAL)) sb.append(", tactical");
    return sb.toString();
  }
}
