  package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.npc.NpcDescription;
import delta.games.lotro.utils.Proxy;

/**
 * 'NPC talk' condition.
 * @author DAM
 */
public class NpcTalkCondition extends ObjectiveCondition
{
  private Proxy<NpcDescription> _npc;

  /**
   * Constructor.
   */
  public NpcTalkCondition()
  {
    _npc=null;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.NPC_TALK;
  }

  /**
   * Get the proxy to the targeted NPC.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<NpcDescription> getProxy()
  {
    return _npc;
  }

  /**
   * Set the proxy to the targeted NPC.
   * @param proxy the proxy to set (may be <code>null</code>).
   */
  public void setProxy(Proxy<NpcDescription> proxy)
  {
    _npc=proxy;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    sb.append(": Talk to ");
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
