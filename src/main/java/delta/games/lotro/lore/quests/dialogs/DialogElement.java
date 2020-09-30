package delta.games.lotro.lore.quests.dialogs;

import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Dialog element.
 * @author DAM
 */
public class DialogElement
{
  private Proxy<NpcDescription> _who;
  private String _what;

  /**
   * Constructor.
   */
  public DialogElement()
  {
    _who=null;
    _what="";
  }

  /**
   * Get the speaker. 
   * @return a proxy to the speaker or <code>null</code>.
   */
  public Proxy<NpcDescription> getWho()
  {
    return _who;
  }

  /**
   * Get the name of the speaker.
   * @return the name of the speaker.
   */
  public String getWhoName()
  {
    return _who!=null?_who.getName():null;
  }

  /**
   * Set the speaker.
   * @param who a proxy to the speaker, or <code>null</code>.
   */
  public void setWho(Proxy<NpcDescription> who)
  {
    _who=who;
  }

  /**
   * Get the text.
   * @return the text.
   */
  public String getWhat()
  {
    return _what;
  }

  /**
   * Set the text.
   * @param what Text to set.
   */
  public void setWhat(String what)
  {
    _what=what;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_who!=null)
    {
      sb.append(_who.getName()).append(" says: ");
    }
    sb.append(_what);
    return sb.toString();
  }
}
