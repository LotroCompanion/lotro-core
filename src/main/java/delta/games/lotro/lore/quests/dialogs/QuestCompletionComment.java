package delta.games.lotro.lore.quests.dialogs;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Interactable;

/**
 * Comments after quest completion.
 * @author DAM
 */
public class QuestCompletionComment
{
  private List<Interactable> _whos;
  private List<String> _whats;

  /**
   * Constructor.
   */
  public QuestCompletionComment()
  {
    _whos=new ArrayList<Interactable>();
    _whats=new ArrayList<String>();
  }

  /**
   * Get the speaker(s). 
   * @return a list of speakers.
   */
  public List<Interactable> getWhos()
  {
    return _whos;
  }

  /**
   * Add a speaker.
   * @param who the speaker to add.
   */
  public void addWho(Interactable who)
  {
    _whos.add(who);
  }

  /**
   * Get the text items.
   * @return the text items.
   */
  public List<String> getWhats()
  {
    return _whats;
  }

  /**
   * Add a text item.
   * @param what Text to add.
   */
  public void addWhat(String what)
  {
    if (what!=null)
    {
      _whats.add(what);
    }
  }

  /**
   * Indicates if this comment is valid or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isValid()
  {
    return ((_whos.size()>0) && (_whats.size()>0));
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    for(Interactable who : _whos)
    {
      if (sb.length()>0)
      {
        sb.append(", ");
      }
      sb.append(who.getName());
    }
    sb.append(" say:").append(EndOfLine.NATIVE_EOL);
    for(String what : _whats)
    {
      sb.append(what).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }
}
