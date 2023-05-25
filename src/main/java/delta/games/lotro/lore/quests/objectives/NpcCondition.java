package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.common.Interactable;
import delta.games.lotro.utils.Proxy;

/**
 * 'NPC xxx' condition.
 * @author DAM
 */
public abstract class NpcCondition extends ObjectiveCondition
{
  private Proxy<Interactable> _npc;

  /**
   * Constructor.
   */
  protected NpcCondition()
  {
    _npc=null;
  }

  /**
   * Get the NPC action.
   * @return a displayable action.
   */
  public abstract String getAction();

  /**
   * Get the proxy to the targeted NPC.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<Interactable> getProxy()
  {
    return _npc;
  }

  /**
   * Set the proxy to the targeted NPC.
   * @param proxy the proxy to set (may be <code>null</code>).
   */
  public void setProxy(Proxy<Interactable> proxy)
  {
    _npc=proxy;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    String action=getAction();
    sb.append(": ").append(action).append(' ');
    if (_npc!=null)
    {
      sb.append(_npc.getName());
    }
    else
    {
      sb.append("???");
    }
    return sb.toString();
  }
}
