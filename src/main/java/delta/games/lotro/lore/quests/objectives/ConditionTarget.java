package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.mobs.MobDescription;
import delta.games.lotro.lore.npc.NpcDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Target of a quest condition (mob or NPC).
 * @author DAM
 */
public class ConditionTarget
{
  private Proxy<NpcDescription> _npc;
  private Proxy<MobDescription> _mob;

  /**
   * Constructor.
   */
  public ConditionTarget()
  {
    _npc=null;
    _mob=null;
  }

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
