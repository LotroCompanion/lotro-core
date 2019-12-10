package delta.games.lotro.lore.npc;

import delta.games.lotro.common.Identifiable;

/**
 * NPC description.
 * @author DAM
 */
public class NpcDescription implements Identifiable
{
  private int _identifier;
  private String _name;
  private String _title;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public NpcDescription(int id, String name)
  {
    _identifier=id;
    if (name==null) name="";
    _name=name;
    _title="";
  }

  /**
   * Get the identifier.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the NPC name.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the NPC title.
   * @return a title.
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
    sb.append(_name);
    if (_title.length()>0)
    {
      sb.append(" (").append(_title).append(')');
    }
    sb.append(" (ID=").append(_identifier).append(')');
    return sb.toString();
  }
}
