package delta.games.lotro.common.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.enums.AICooldownChannel;
import delta.games.lotro.common.enums.AIHint;

/**
 * Entry of an action table.
 * @author DAM
 */
public class ActionTableEntry
{
  private float _probability;
  // Self data/constraints
  private Float _cooldown;
  private Set<AIHint> _requiredHints;
  private Set<AIHint> _disallowedHints;
  private AICooldownChannel _cooldownChannel;
  // Target data/constraints
  private Float _targetCooldown;
  private Set<AIHint> _targetRequiredHints;
  private Set<AIHint> _targetDisallowedHints;
  // Actions chain
  private List<ActionEntry> _actionsChain;

  // Maybe:
  //Property: AIAction_CustomTarget, ID=268437728, type=Enum Mapper
  //Property: AIAction_InitialCooldown, ID=268461864, type=Float
  // No:
  //Property: AIAction_CallbackScriptlet, ID=268437507, type=Data File ID
  //Property: AIAction_ColorEventType, ID=268438174, type=Enum Mapper

  /**
   * Constructor.
   */
  public ActionTableEntry()
  {
    _probability=0;
    _cooldown=null;
    _requiredHints=null;
    _disallowedHints=null;
    _cooldownChannel=null;
    _targetCooldown=null;
    _targetRequiredHints=null;
    _targetDisallowedHints=null;
    _actionsChain=new ArrayList<ActionEntry>();
  }

  /**
   * Get the probability;
   * @return the probability.
   */
  public float getProbability()
  {
    return _probability;
  }

  /**
   * Get the (self) cooldown.
   * @return A cooldown or <code>null</code>.
   */
  public Float getCooldown()
  {
    return _cooldown;
  }

  /**
   * Get the required (self) hints.
   * @return a non-empty set of hints, or <code>null</code>.
   */
  public Set<AIHint> getRequiredHints()
  {
    return _requiredHints;
  }

  /**
   * Get the disallowed (self) hints.
   * @return a non-empty set of hints, or <code>null</code>.
   */
  public Set<AIHint> getDisallowedHints()
  {
    return _disallowedHints;
  }

  /**
   * Get the cooldown channel.
   * @return a cooldown channel or <code>null</code>.
   */
  public AICooldownChannel getCooldownChannel()
  {
    return _cooldownChannel;
  }

  /**
   * @return the targetCooldown
   */
  public Float getTargetCooldown()
  {
    return _targetCooldown;
  }

  /**
   * Get the required (target) hints.
   * @return a non-empty set of hints, or <code>null</code>.
   */
  public Set<AIHint> getTargetRequiredHints()
  {
    return _targetRequiredHints;
  }

  /**
   * Get the disallowed (target) hints.
   * @return a non-empty set of hints, or <code>null</code>.
   */
  public Set<AIHint> getTargetDisallowedHints()
  {
    return _targetDisallowedHints;
  }

  /**
   * @return the actionsChain
   */
  public List<ActionEntry> getActionsChain()
  {
    return _actionsChain;
  }
}
