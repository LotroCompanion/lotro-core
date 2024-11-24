package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.enums.EffectAuraType;

/**
 * Aura effect.
 * @author DAM
 */
public class AuraEffect extends Effect
{
  private EffectAuraType _type;
  private boolean _shouldAffectCaster;
  private List<EffectGenerator> _appliedEffects;

  /**
   * Constructor.
   */
  public AuraEffect()
  {
    _type=null;
    _shouldAffectCaster=true;
    _appliedEffects=new ArrayList<EffectGenerator>();
  }

  /**
   * Get the aura type.
   * @return A type.
   */
  public EffectAuraType getType()
  {
    return _type;
  }

  /**
   * Set the aura type.
   * @param type Type to set.
   */
  public void setType(EffectAuraType type)
  {
    _type=type;
  }

  /**
   * Indicates if this effect should affect the caster or not.
   * @return <code>true</code> if it should, <code>false</code> otherwise.
   */
  public boolean shouldAffectCaster()
  {
    return _shouldAffectCaster;
  }

  /**
   * Set the 'should affect caster' flag.
   * @param shoudlAffectCaster Value to set.
   */
  public void setShouldAffectCaster(boolean shoudlAffectCaster)
  {
    _shouldAffectCaster=shoudlAffectCaster;
  }

  /**
   * Get the applied effects.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getAppliedEffects()
  {
    return _appliedEffects;
  }

  /**
   * Add a generator.
   * @param generator Generator to add.
   */
  public void addAppliedEffect(EffectGenerator generator)
  {
    _appliedEffects.add(generator);
  }
}
