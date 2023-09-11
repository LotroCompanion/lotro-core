package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.enums.SkillType;

/**
 * Effect that has a chance to trigger other effects.
 * @author DAM
 */
public class ProcEffect implements EffectAspect
{
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
    _skillTypes=new ArrayList<SkillType>();
    _procProbability=null;
    _procedEffects=new ArrayList<EffectGenerator>();
    _cooldown=null;
  }

  public List<SkillType> getSkillTypes()
  {
    return _skillTypes;
  }

  public void setSkillTypes(List<SkillType> skillTypes)
  {
    _skillTypes.clear();
    _skillTypes.addAll(skillTypes);
  }

  public void setProcProbability(Float probability)
  {
    _procProbability=probability;
  }

  public Float getProcProbability()
  {
    return _procProbability;
  }

  public void addProcedEffect(EffectGenerator generator)
  {
    _procedEffects.add(generator);
  }

  public List<EffectGenerator> getProcedEffects()
  {
    return _procedEffects;
  }

  public void setCooldown(Float cooldown)
  {
    _cooldown=cooldown;
  }

  public Float getCooldown()
  {
    return _cooldown;
  }
}
