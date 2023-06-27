package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.common.Interactable;

/**
 * 'NPC xxx' condition.
 * @author DAM
 */
public abstract class NpcCondition extends ObjectiveCondition
{
  private Interactable _npc;

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
   * Get the targeted NPC.
   * @return a NPC or <code>null</code>.
   */
  public Interactable getNpc()
  {
    return _npc;
  }

  /**
   * Set the targeted NPC.
   * @param npc the NPC to set (may be <code>null</code>).
   */
  public void setNpc(Interactable npc)
  {
    _npc=npc;
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
