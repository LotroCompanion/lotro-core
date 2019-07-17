package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.mobs.MobDescription;
import delta.games.lotro.lore.npc.NpcDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Base class for 'Detection' conditions.
 * @author DAM
 */
public abstract class DetectionCondition extends ObjectiveCondition
{
  private Proxy<NpcDescription> _npc;
  private Proxy<MobDescription> _mob;

  /**
   * Constructor.
   */
  public DetectionCondition()
  {
    _npc=null;
    _mob=null;
  }

  /**
   * Get the action.
   * @return a displayable action.
   */
  public abstract String getAction();

  /**
   * Get the proxy to the targeted NPC.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<NpcDescription> getNpcProxy()
  {
    return _npc;
  }

  /**
   * Set the proxy to the targeted NPC.
   * @param proxy the proxy to set (may be <code>null</code>).
   */
  public void setNpcProxy(Proxy<NpcDescription> proxy)
  {
    _npc=proxy;
  }

  /**
   * Get the proxy to the targeted mob.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<MobDescription> getMobProxy()
  {
    return _mob;
  }

  /**
   * Set the proxy to the targeted NPC.
   * @param proxy the proxy to set (may be <code>null</code>).
   */
  public void setMobProxy(Proxy<MobDescription> proxy)
  {
    _mob=proxy;
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
      sb.append("NPC ").append(_npc.getName());
    }
    else if (_mob!=null)
    {
      sb.append("mob ").append(_mob.getName());
    }
    else
    {
      sb.append("???");
    }
    return sb.toString();
  }
}
