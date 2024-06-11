package delta.games.lotro.common.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
  private List<AIHint> _requiredHints;
  private List<AIHint> _disallowedHints;
  private AICooldownChannel _cooldownChannel;
  // Target data/constraints
  private Float _targetCooldown;
  private List<AIHint> _targetRequiredHints;
  private List<AIHint> _targetDisallowedHints;
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
    _requiredHints=Collections.emptyList();
    _disallowedHints=Collections.emptyList();
    _cooldownChannel=null;
    _targetCooldown=null;
    _targetRequiredHints=Collections.emptyList();
    _targetDisallowedHints=Collections.emptyList();
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
   * Set the probability.
   * @param probability Probability to set.
   */
  public void setProbability(float probability)
  {
    _probability=probability;
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
   * Set the (self) cooldown.
   * @param cooldown Cooldown to set (may be <code>null</code>).
   */
  public void setCooldown(Float cooldown)
  {
    _cooldown=cooldown;
  }

  /**
   * Get the required (self) hints.
   * @return a list of hints.
   */
  public List<AIHint> getRequiredHints()
  {
    return _requiredHints;
  }

  /**
   * Set the required hints.
   * @param hints Hints to set.
   */
  public void setRequiredHints(List<AIHint> hints)
  {
    if (hints==null)
    {
      hints=Collections.emptyList();
    }
    _requiredHints=hints;
  }

  /**
   * Get the disallowed (self) hints.
   * @return a list of hints.
   */
  public List<AIHint> getDisallowedHints()
  {
    return _disallowedHints;
  }

  /**
   * Set the required hints.
   * @param hints Hints to set.
   */
  public void setDisallowedHints(List<AIHint> hints)
  {
    if (hints==null)
    {
      hints=Collections.emptyList();
    }
    _disallowedHints=hints;
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
   * Set the cooldown channel.
   * @param cooldownChannel Cooldown channel to set (may be <code>null</code>).
   */
  public void setCooldownChannel(AICooldownChannel cooldownChannel)
  {
    _cooldownChannel=cooldownChannel;
  }

  /**
   * Get the target cooldown.
   * @return A cooldown or <code>null</code>.
   */
  public Float getTargetCooldown()
  {
    return _targetCooldown;
  }

  /**
   * Set the target cooldown.
   * @param targetCooldown Cooldown to set (may be <code>null</code>).
   */
  public void setTargetCooldown(Float targetCooldown)
  {
    _targetCooldown=targetCooldown;
  }

  /**
   * Get the required (target) hints.
   * @return a list of hints.
   */
  public List<AIHint> getTargetRequiredHints()
  {
    return _targetRequiredHints;
  }

  /**
   * Set the target required hints.
   * @param hints Hints to set.
   */
  public void setTargetRequiredHints(List<AIHint> hints)
  {
    if (hints==null)
    {
      hints=Collections.emptyList();
    }
    _targetRequiredHints=hints;
  }

  /**
   * Get the disallowed (target) hints.
   * @return a list of hints.
   */
  public List<AIHint> getTargetDisallowedHints()
  {
    return _targetDisallowedHints;
  }

  /**
   * Set the target disallowed hints.
   * @param hints Hints to set.
   */
  public void setTargetDisallowedHints(List<AIHint> hints)
  {
    if (hints==null)
    {
      hints=Collections.emptyList();
    }
    _targetDisallowedHints=hints;
  }

  /**
   * Get the actions chain.
   * @return a list of action entries.
   */
  public List<ActionEntry> getActionsChain()
  {
    return _actionsChain;
  }

  /**
   * Add an action.
   * @param action Action to add.
   */
  public void addAction(ActionEntry action)
  {
    _actionsChain.add(action);
  }
}
