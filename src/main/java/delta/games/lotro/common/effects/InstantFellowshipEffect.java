package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Instant fellowship effect.
 * @author DAM
 */
public class InstantFellowshipEffect extends InstantEffect implements ParentEffect
{
  private List<EffectGenerator> _effects;
  private boolean _applyToRaidGroups;
  private boolean _applyToPets;
  private boolean _applyToTarget;
  private String _fellowshipStringOverride;
  private Float _range;

  /**
   * Constructor.
   */
  public InstantFellowshipEffect()
  {
    super();
    _effects=new ArrayList<EffectGenerator>();
    _applyToRaidGroups=false;
    _applyToPets=true;
    _applyToTarget=true;
    _fellowshipStringOverride=null;
    _range=null;
  }

  /**
   * Add a generated effect.
   * @param generator Generator to add.
   */
  public void addEffect(EffectGenerator generator)
  {
    _effects.add(generator);
  }

  /**
   * Get the managed effects.
   * @return A list of effects.
   */
  public List<EffectGenerator> getEffects()
  {
    return _effects;
  }

  /**
   * Indicates if this effect applies to raid groups.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean appliesToRaidGroups()
  {
    return _applyToRaidGroups;
  }

  /**
   * Set the 'apply to raid groups' flag.
   * @param applyToRaidGroups Flag to set.
   */
  public void setAppliesToRaidGroups(boolean applyToRaidGroups)
  {
    _applyToRaidGroups=applyToRaidGroups;
  }

  /**
   * Indicates if this effect applies to pets.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean appliesToPets()
  {
    return _applyToPets;
  }

  /**
   * Set the 'apply to pets' flag.
   * @param applyToPets Flag to set.
   */
  public void setAppliesToPets(boolean applyToPets)
  {
    _applyToPets=applyToPets;
  }

  /**
   * Indicates if this effect applies to target.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean appliesToTarget()
  {
    return _applyToTarget;
  }

  /**
   * Set the 'apply to target' flag.
   * @param applyToTarget Flag to set.
   */
  public void setAppliesToTarget(boolean applyToTarget)
  {
    _applyToTarget=applyToTarget;
  }

  /**
   * Get the string override for the fellowship.
   * @return an override or <code>null</code>.
   */
  public String getFellowshipStringOverride()
  {
    return _fellowshipStringOverride;
  }

  /**
   * Set the string override for the fellowship.
   * @param fellowshipStringOverride the override to set.
   */
  public void setFellowshipStringOverride(String fellowshipStringOverride)
  {
    _fellowshipStringOverride=fellowshipStringOverride;
  }

  /**
   * Get the range.
   * @return a range.
   */
  public Float getRange()
  {
    return _range;
  }

  /**
   * Set the range.
   * @param range Range to set.
   */
  public void setRange(float range)
  {
    _range=Float.valueOf(range);
  }

  @Override
  public Set<Effect> getChildEffects()
  {
    HashSet<Effect> ret=new HashSet<Effect>();
    for(EffectGenerator generator : _effects)
    {
      ret.add(generator.getEffect());
    }
    return ret;
  }
}
