package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.lore.agents.AgentDescription;
import delta.games.lotro.utils.Proxy;

/**
 * AI pet effect.
 * @author DAM
 */
public class AIPetEffect extends InstantEffect implements ParentEffect
{
  // - NPC or Mob
  private Proxy<AgentDescription> _agent;
  private List<EffectGenerator> _startupEffects;
  private List<EffectGenerator> _applyToMasterEffects;

  /**
   * Constructor.
   */
  public AIPetEffect()
  {
    super();
    _agent=null;
    _startupEffects=new ArrayList<EffectGenerator>();
    _applyToMasterEffects=new ArrayList<EffectGenerator>();
  }

  /**
   * Get the agent, if any.
   * @return An agent or <code>null</code>.
   */
  public Proxy<AgentDescription> getAgent()
  {
    return _agent;
  }

  /**
   * Set the agent.
   * @param agent Agent to set.
   */
  public void setAgent(Proxy<AgentDescription> agent)
  {
    _agent=agent;
  }

  /**
   * Add a startup effect.
   * @param generator Effect generator.
   */
  public void addStartupEffect(EffectGenerator generator)
  {
    _startupEffects.add(generator);
  }

  /**
   * Get the startup effects.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getStartupEffects()
  {
    return _startupEffects;
  }

  /**
   * Add a 'apply to master' effect.
   * @param generator Effect generator.
   */
  public void addApplyToMasterEffect(EffectGenerator generator)
  {
    _applyToMasterEffects.add(generator);
  }

  /**
   * Get the 'apply to master' effects.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getApplyToMasterEffects()
  {
    return _applyToMasterEffects;
  }

  @Override
  public Set<Effect> getChildEffects()
  {
    HashSet<Effect> ret=new HashSet<Effect>();
    if (!_startupEffects.isEmpty())
    {
      for(EffectGenerator generator : _startupEffects)
      {
        ret.add(generator.getEffect());
      }
    }
    if (!_applyToMasterEffects.isEmpty())
    {
      for(EffectGenerator generator : _applyToMasterEffects)
      {
        ret.add(generator.getEffect());
      }
    }
    return ret;
  }
}
