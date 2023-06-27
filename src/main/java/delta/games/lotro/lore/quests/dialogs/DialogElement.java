package delta.games.lotro.lore.quests.dialogs;

import delta.games.lotro.common.Interactable;

/**
 * Dialog element.
 * @author DAM
 */
public class DialogElement
{
  private Interactable _who;
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
   * @return a speaker or <code>null</code>.
   */
  public Interactable getWho()
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
   * @param who a speaker, or <code>null</code>.
   */
  public void setWho(Interactable who)
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
