package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.agents.AgentDescription;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.npcs.NpcDescription;

/**
 * Target of a quest condition (mob or NPC).
 * @author DAM
 */
public class ConditionTarget
{
  private AgentDescription _agent;

  /**
   * Constructor.
   */
  public ConditionTarget()
  {
    _agent=null;
  }

  /**
   * Get the targeted agent.
   * @return an agent or <code>null</code>.
   */
  public AgentDescription getAgent()
  {
    return _agent;
  }

  /**
   * Set the targeted agent.
   * @param agent the Agent to set (may be <code>null</code>).
   */
  public void setAgent(AgentDescription agent)
  {
    _agent=agent;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_agent instanceof NpcDescription)
    {
      sb.append("NPC ").append(_agent.getName());
    }
    else if (_agent instanceof MobDescription)
    {
      sb.append("mob ").append(_agent.getName());
    }
    else
    {
      sb.append("???");
    }
    return sb.toString();
  }
}
