package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.enums.SubSpecies;
import delta.games.lotro.common.properties.ModPropertyList;

/**
 * Effect that triggers other effects on kill events.
 * @author DAM
 */
public class KillProcEffect extends PropertyModificationEffect implements ParentEffect
{
  private List<EffectGenerator> _casterEffects;
  private List<EffectGenerator> _userEffects;
  private float _cooldown;
  private ModPropertyList _cooldownModifiers;
  private float _probability;
  private ModPropertyList _probabilityModifiers;
  private boolean _requiresKillShot;
  private boolean _onSelfKilled;
  private SubSpecies _targetRequiredSpecies;

  /**
   * Constructor.
   */
  public KillProcEffect()
  {
    super();
    _casterEffects=new ArrayList<EffectGenerator>();
    _userEffects=new ArrayList<EffectGenerator>();
    _cooldown=0;
    _cooldownModifiers=null;
    _probability=0;
    _probabilityModifiers=null;
    _requiresKillShot=false;
    _onSelfKilled=false;
    _targetRequiredSpecies=null;
  }

  /**
   * Add a 'caster' effect.
   * @param generator Effect generator.
   */
  public void addCasterEffect(EffectGenerator generator)
  {
    _casterEffects.add(generator);
  }

  /**
   * Get the 'caster' effects.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getCasterEffects()
  {
    return _casterEffects;
  }

  /**
   * Add a 'user' effect.
   * @param generator Effect generator.
   */
  public void addUserEffect(EffectGenerator generator)
  {
    _userEffects.add(generator);
  }

  /**
   * Get the 'user' effects.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getUserEffects()
  {
    return _userEffects;
  }

  /**
   * Get the cooldown.
   * @return a duration (seconds).
   */
  public float getCooldown()
  {
    return _cooldown;
  }

  /**
   * Set the cooldown.
   * @param cooldown the duration to set.
   */
  public void setCooldown(float cooldown)
  {
    _cooldown=cooldown;
  }

  /**
   * Get the cooldown modifiers.
   * @return the cooldown modifiers.
   */
  public ModPropertyList getCooldownModifiers()
  {
    return _cooldownModifiers;
  }

  /**
   * Set the cooldown modifiers.
   * @param cooldownModifiers the cooldown modifiers to set.
   */
  public void setCooldownModifiers(ModPropertyList cooldownModifiers)
  {
    _cooldownModifiers=cooldownModifiers;
  }

  /**
   * Get the raw proc probability.
   * @return a probability.
   */
  public float getProbability()
  {
    return _probability;
  }

  /**
   * Set the raw proc probability.
   * @param probability the probability to set.
   */
  public void setProbability(float probability)
  {
    _probability=probability;
  }

  /**
   * Get the proc probability modifiers.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getProbabilityModifiers()
  {
    return _probabilityModifiers;
  }

  /**
   * Set the probability modifiers.
   * @param probabilityModifiers the probability modifiers to set (may be <code>null</code>).
   */
  public void setProbabilityModifiers(ModPropertyList probabilityModifiers)
  {
    _probabilityModifiers=probabilityModifiers;
  }

  /**
   * Get the 'requires kill shot' flag.
   * @return the flag value.
   */
  public boolean isRequiresKillShot()
  {
    return _requiresKillShot;
  }

  /**
   * Set the 'requires kill shot' flag.
   * @param requiresKillShot the flag value to set.
   */
  public void setRequiresKillShot(boolean requiresKillShot)
  {
    _requiresKillShot=requiresKillShot;
  }

  /**
   * Get the 'on self kill' flag.
   * @return the flag value.
   */
  public boolean isOnSelfKilled()
  {
    return _onSelfKilled;
  }

  /**
   * Set the 'on self killed' flag.
   * @param onSelfKilled the flag value to set.
   */
  public void setOnSelfKilled(boolean onSelfKilled)
  {
    _onSelfKilled=onSelfKilled;
  }

  /**
   * Get the required species of the target.
   * @return a species or <code>null</code>.
   */
  public SubSpecies getTargetRequiredSpecies()
  {
    return _targetRequiredSpecies;
  }

  /**
   * Set the required species of the target.
   * @param targetRequiredSpecies species to set.
   */
  public void setTargetRequiredSpecies(SubSpecies targetRequiredSpecies)
  {
    _targetRequiredSpecies=targetRequiredSpecies;
  }

  @Override
  public Set<Effect> getChildEffects()
  {
    HashSet<Effect> ret=new HashSet<Effect>();
    for(EffectGenerator generator : _casterEffects)
    {
      ret.add(generator.getEffect());
    }
    for(EffectGenerator generator : _userEffects)
    {
      ret.add(generator.getEffect());
    }
    return ret;
  }
}
