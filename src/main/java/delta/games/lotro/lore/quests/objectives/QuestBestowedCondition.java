package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.utils.Proxy;

/**
 * Quest(s)/deed bestowed condition.
 * @author DAM
 */
public class QuestBestowedCondition extends ObjectiveCondition
{
  private Proxy<Achievable> _achievable;

  /**
   * Constructor.
   */
  public QuestBestowedCondition()
  {
    _achievable=null;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.QUEST_BESTOWED;
  }

  /**
   * Get the proxy to the targeted achievable.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<Achievable> getProxy()
  {
    return _achievable;
  }

  /**
   * Set the proxy to the targeted achievable.
   * @param proxy the proxy to set (may be <code>null</code>).
   */
  public void setProxy(Proxy<Achievable> proxy)
  {
    _achievable=proxy;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    sb.append(": Have ");
    if (_achievable!=null)
    {
      sb.append("quest/deed: ").append(_achievable);
    }
    sb.append(" bestowed");
    return sb.toString();
  }
}
