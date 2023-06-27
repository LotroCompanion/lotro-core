package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Emote condition.
 * @author DAM
 */
public class EmoteCondition extends ObjectiveCondition
{
  private EmoteDescription _emote;
  private Integer _maxDaily;
  private ConditionTarget _target;

  /**
   * Constructor.
   */
  public EmoteCondition()
  {
    _emote=null;
    _maxDaily=null;
    _target=null;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.EMOTE;
  }

  /**
   * Get the targeted emote.
   * @return an emote.
   */
  public EmoteDescription getEmote()
  {
    return _emote;
  }

  /**
   * Set the targeted emote.
   * @param emote the emote to set (may be <code>null</code>).
   */
  public void setEmote(EmoteDescription emote)
  {
    _emote=emote;
  }

  /**
   * Get the maximum daily count.
   * @return a count or <code>null</code> if no limit.
   */
  public Integer getMaxDaily()
  {
    return _maxDaily;
  }

  /**
   * Set the maximum daily count.
   * @param maxDaily the count to set.
   */
  public void setMaxDaily(Integer maxDaily)
  {
    _maxDaily=maxDaily;
  }

  /**
   * Get the target.
   * @return a target or <code>null</code>.
   */
  public ConditionTarget getTarget()
  {
    return _target;
  }

  /**
   * Set the target.
   * @param target the target to set (may be <code>null</code>).
   */
  public void setTarget(ConditionTarget target)
  {
    _target=target;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    sb.append(": Perform emote ").append(_emote);
    int count=getCount();
    if (count>1)
    {
      sb.append(" x").append(count);
    }
    if (_maxDaily!=null)
    {
      sb.append(" (max ").append(_maxDaily).append("/day");
    }
    if (_target!=null)
    {
      sb.append(" on ");
      sb.append(_target);
    }
    return sb.toString();
  }
}
