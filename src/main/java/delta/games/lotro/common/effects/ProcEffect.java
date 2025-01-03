package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.enums.SkillType;

/**
 * Effect that has a chance to trigger other effects.
 * @author DAM
 */
public class ProcEffect extends PropertyModificationEffect implements ParentEffect
{
  // Class 3686
  /**
   * Types of skills that may trigger effects.
   */
  private List<SkillType> _skillTypes;
  /**
   * Probability to trigger effects (0..1).
   */
  private Float _procProbability;
  /**
   * Triggered effects.
   */
  private List<EffectGenerator> _procedEffects;
  /**
   * Minimum time between triggers (s).
   */
  private Float _cooldown;

  /**
   * Constructor.
   */
  public ProcEffect()
  {
    super();
    _skillTypes=new ArrayList<SkillType>();
    _procProbability=null;
    _procedEffects=new ArrayList<EffectGenerator>();
    _cooldown=null;
  }

  /**
   * Get the skill types that triggers.
   * @return a list of skill types.
   */
  public List<SkillType> getSkillTypes()
  {
    return _skillTypes;
  }

  /**
   * Set the skill types.
   * @param skillTypes Skill types to use.
   */
  public void setSkillTypes(List<SkillType> skillTypes)
  {
    _skillTypes.clear();
    _skillTypes.addAll(skillTypes);
  }

  /**
   * Set the probability to trigger.
   * @param probability Probability to set (0-1).
   */
  public void setProcProbability(Float probability)
  {
    _procProbability=probability;
  }

  /**
   * Get the probability to trigger.
   * @return A probability (0-1).
   */
  public Float getProcProbability()
  {
    return _procProbability;
  }

  /**
   * Add a triggered effect.
   * @param generator Effect generator.
   */
  public void addProcedEffect(EffectGenerator generator)
  {
    _procedEffects.add(generator);
  }

  /**
   * Get the triggered effects.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getProcedEffects()
  {
    return _procedEffects;
  }

  /**
   * Set the cooldown (minimum time between triggers).
   * @param cooldown A duration (seconds).
   */
  public void setCooldown(Float cooldown)
  {
    _cooldown=cooldown;
  }

  /**
   * Get the cooldown.
   * @return A duration (seconds) or <code>null</code>.
   */
  public Float getCooldown()
  {
    return _cooldown;
  }

  @Override
  public String resolveVariable(String variableName)
  {
    if ("PROCPROBABILITY".equals(variableName))
    {
      float procProbability=(_procProbability!=null)?_procProbability.floatValue():0;
      int percent=(int)(procProbability*100);
      return percent+"%";
    }
    return variableName;
  }

  @Override
  public Set<Effect> getChildEffects()
  {
    HashSet<Effect> ret=new HashSet<Effect>();
    for(EffectGenerator generator : _procedEffects)
    {
      ret.add(generator.getEffect());
    }
    return ret;
  }
}
