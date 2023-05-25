package delta.games.lotro.lore.agents.npcs;

import delta.games.lotro.lore.agents.AgentDescription;

/**
 * NPC description.
 * @author DAM
 */
public class NpcDescription extends AgentDescription
{
  private String _title;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public NpcDescription(int id, String name)
  {
    super(id,name);
    _title="";
  }

  /**
   * Get the NPC title.
   * @return a title (may be empty but not <code>null</code>).
   */
  public String getTitle()
  {
    return _title;
  }

  /**
   * Set the NPC title.
   * @param title Title to set.
   */
  public void setTitle(String title)
  {
    if (title==null) title="";
    _title=title;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("NPC ");
    sb.append(getName());
    if (_title.length()>0)
    {
      sb.append(" (").append(_title).append(')');
    }
    sb.append(" (ID=").append(getIdentifier()).append(')');
    return sb.toString();
  }
}
