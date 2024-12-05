package delta.games.lotro.lore.agents.npcs;

import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.lore.agents.AgentDescription;

/**
 * NPC description.
 * @author DAM
 */
public class NpcDescription extends AgentDescription
{
  private String _title;
  private CharacterSex _gender;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public NpcDescription(int id, String name)
  {
    super(id,name);
    _title="";
    _gender=null;
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

  /**
   * Get the NPC gender.
   * @return a gender (<code>null</code> if not known).
   */
  public CharacterSex getGender()
  {
    return _gender;
  }

  /**
   * Set the NPC gender.
   * @param gender Gender to set.
   */
  public void setGender(CharacterSex gender)
  {
    _gender=gender;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("NPC ");
    sb.append(getName());
    if (_gender!=null)
    {
      sb.append(" (").append(_gender.getLabel()).append(')');
    }
    if (!_title.isEmpty())
    {
      sb.append(" (").append(_title).append(')');
    }
    sb.append(" (ID=").append(getIdentifier()).append(')');
    return sb.toString();
  }
}
