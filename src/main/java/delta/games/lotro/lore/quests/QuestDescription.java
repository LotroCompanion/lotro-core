package delta.games.lotro.lore.quests;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.LockType;
import delta.games.lotro.common.Repeatability;
import delta.games.lotro.common.Size;
import delta.games.lotro.common.enums.QuestCategory;
import delta.games.lotro.common.enums.QuestScope;
import delta.games.lotro.lore.quests.dialogs.DialogElement;
import delta.games.lotro.lore.quests.dialogs.QuestCompletionComment;
import delta.games.lotro.utils.Proxy;

/**
 * LOTRO quest description.
 * @author DAM
 */
public class QuestDescription extends Achievable
{
  /**
   * Category.
   */
  private QuestCategory _category; 
  /**
   * Scope.
   */
  private QuestScope _scope;
  /**
   * Quest arc. Can be empty be not <code>null</code>.
   */
  private String _questArc;
  /**
   * Recommended size: solo, small fellowship, fellowship or raid.
   */
  private Size _size;
  /**
   * Repeatability (never null).
   */
  private Repeatability _repeatability;
  /**
   * Lock type.
   */
  private LockType _lockType;

  // Flags
  private boolean _instanced;
  private boolean _shareable;
  private boolean _sessionPlay;
  private boolean _autoBestowed;

  // Bestowers
  private List<DialogElement> _bestowers;
  // Links
  private Proxy<Achievable> _nextQuest;
  // End of quest dialogs
  private List<DialogElement> _endDialogs;
  // Completion comments
  private List<QuestCompletionComment> _completionComments;

  /**
   * Constructor.
   */
  public QuestDescription()
  {
    super();
    _scope=null;
    _questArc="";
    _size=Size.SOLO;
    _repeatability=Repeatability.NOT_REPEATABLE;
    _lockType=null;
    _instanced=false;
    _shareable=true;
    _sessionPlay=false;
    _autoBestowed=false;
    _bestowers=new ArrayList<DialogElement>();
    _nextQuest=null;
    _endDialogs=new ArrayList<DialogElement>();
    _completionComments=new ArrayList<QuestCompletionComment>();
  }

  /**
   * Get the category of this quest.
   * @return the category of this quest.
   */
  public QuestCategory getCategory()
  {
    return _category;
  }

  /**
   * Set the category of this quest. 
   * @param category the category to set.
   */
  public void setCategory(QuestCategory category)
  {
    _category=category;
  }

  /**
   * Get the scope of this quest.
   * @return the scope of this quest.
   */
  public QuestScope getQuestScope()
  {
    return _scope;
  }

  /**
   * Set the scope of this quest.
   * @param scope the scope to set.
   */
  public void setQuestScope(QuestScope scope)
  {
    _scope=scope;
  }

  /**
   * Get the arc of this quest.
   * @return the arc of this quest.
   */
  public String getQuestArc()
  {
    return _questArc;
  }

  /**
   * Set the arc of this quest.
   * @param questArc the arc to set.
   */
  public void setQuestArc(String questArc)
  {
    if (questArc==null) questArc="";
    _questArc=questArc;
  }

  /**
   * Get the quest size.
   * @return the quest size.
   */
  public Size getSize()
  {
    return _size;
  }

  /**
   * Set the quest size.
   * @param size the size to set.
   */
  public void setSize(Size size)
  {
    _size=size;
  }

  /**
   * Get the repeatability.
   * @return A repeatability.
   */
  public Repeatability getRepeatability()
  {
    return _repeatability;
  }

  /**
   * Set the repeatability.
   * @param repeatability value to set.
   */
  public void setRepeatability(Repeatability repeatability)
  {
    _repeatability=repeatability;
  }

  /**
   * Get the lock type.
   * @return A lock type.
   */
  public LockType getLockType()
  {
    return _lockType;
  }

  /**
   * Set the lock type.
   * @param lockType value to set.
   */
  public void setLockType(LockType lockType)
  {
    _lockType=lockType;
  }

  /**
   * Indicates if this quest is instanced or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isInstanced()
  {
    return _instanced;
  }

  /**
   * Set the 'instanced' flag.
   * @param instanced value to set.
   */
  public void setInstanced(boolean instanced)
  {
    _instanced=instanced;
  }

  /**
   * Indicates if this quest is shareable or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isShareable()
  {
    return _shareable;
  }

  /**
   * Set the 'shareable' flag.
   * @param shareable value to set.
   */
  public void setShareable(boolean shareable)
  {
    _shareable=shareable;
  }

  /**
   * Indicates if this quest is a session play or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isSessionPlay()
  {
    return _sessionPlay;
  }

  /**
   * Set the 'session play' flag.
   * @param sessionPlay value to set.
   */
  public void setSessionPlay(boolean sessionPlay)
  {
    _sessionPlay=sessionPlay;
  }

  /**
   * Indicates if this quest is automatically bestowed or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isAutoBestowed()
  {
    return _autoBestowed;
  }

  /**
   * Set the 'auto bestowed' flag.
   * @param autoBestowed value to set.
   */
  public void setAutoBestowed(boolean autoBestowed)
  {
    _autoBestowed=autoBestowed;
  }

  /**
   * Get the bestowers of this quest.
   * @return the bestowers of this quest.
   */
  public List<DialogElement> getBestowers()
  {
    return _bestowers;
  }

  /**
   * Add a bestower.
   * @param bestower Bestower to add.
   */
  public void addBestower(DialogElement bestower)
  {
    _bestowers.add(bestower);
  }

  /**
   * Get the 'next' quest for this quest. 
   * @return a proxy or <code>null</code>.
   */
  public Proxy<Achievable> getNextQuest()
  {
    return _nextQuest;
  }

  /**
   * Set the 'next' quest.
   * @param nextQuest proxy to set as a 'next' quest.
   */
  public void setNextQuest(Proxy<Achievable> nextQuest)
  {
    _nextQuest=nextQuest;
  }

  /**
   * Get the end dialogs for this quest.
   * @return the end dialogs for this quest.
   */
  public List<DialogElement> getEndDialogs()
  {
    return _endDialogs;
  }

  /**
   * Add an end dialog.
   * @param dialog Dialog to add.
   */
  public void addEndDialog(DialogElement dialog)
  {
    _endDialogs.add(dialog);
  }

  /**
   * Get completion comments for this quest.
   * @return the completion comments for this quest.
   */
  public List<QuestCompletionComment> getCompletionComments()
  {
    return _completionComments;
  }

  /**
   * Add a quest completion comment.
   * @param comment Comment to add.
   */
  public void addCompletionComment(QuestCompletionComment comment)
  {
    _completionComments.add(comment);
  }

  /**
   * Dump the contents of this quest as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    super.dumpFirstLine(sb);
    if (_size!=Size.SOLO)
    {
      sb.append(" (");
      sb.append(_size);
      sb.append(')');
    }
    if (_repeatability!=Repeatability.NOT_REPEATABLE)
    {
      sb.append(" (").append(_repeatability).append(")");
    }
    if (_lockType!=null)
    {
      sb.append(" (").append(_lockType).append(")");
    }
    if (_instanced)
    {
      sb.append(" (instanced)");
    }
    if (!_shareable)
    {
      sb.append(" (not shareable)");
    }
    if (_sessionPlay)
    {
      sb.append(" (session play)");
    }
    if (_autoBestowed)
    {
      sb.append(" (auto-bestowed)");
    }
    sb.append(EndOfLine.NATIVE_EOL);
    if (_category!=null)
    {
      sb.append("Category: ").append(_category).append(EndOfLine.NATIVE_EOL);
    }
    super.dumpOtherLines(sb);
    if (_scope!=null)
    {
      sb.append("Scope: ").append(_scope).append(EndOfLine.NATIVE_EOL);
    }
    if (!_questArc.isEmpty())
    {
      sb.append("Arc: ").append(_questArc).append(EndOfLine.NATIVE_EOL);
    }
    if (_nextQuest!=null)
    {
      sb.append("Next quest: ").append(_nextQuest).append(EndOfLine.NATIVE_EOL);
    }
    // Bestowers
    dumpBestowers(sb);
    // End dialogs
    dumpEndDialogs(sb);
    // Quest completion comments
    dumpCompletionComments(sb);
    return sb.toString().trim();
  }

  private void dumpBestowers(StringBuilder sb)
  {
    int nbBestowers=_bestowers.size();
    if (nbBestowers>0)
    {
      for(DialogElement bestower : _bestowers)
      {
        String name=bestower.getWhoName();
        sb.append("Bestower: ").append(name).append(EndOfLine.NATIVE_EOL);
        String text=bestower.getWhat();
        sb.append("Bestower text: ").append(text).append(EndOfLine.NATIVE_EOL);
      }
    }
  }

  private void dumpEndDialogs(StringBuilder sb)
  {
    int nbEndDialogs=_endDialogs.size();
    if (nbEndDialogs>0)
    {
      for(DialogElement endDialog : _endDialogs)
      {
        String name=endDialog.getWhoName();
        sb.append("End dialog: ");
        if (name!=null)
        {
          sb.append(name).append(" says: ");
        }
        String text=endDialog.getWhat();
        sb.append(text).append(EndOfLine.NATIVE_EOL);
      }
    }
  }

  private void dumpCompletionComments(StringBuilder sb)
  {
    int nbComments=_completionComments.size();
    if (nbComments>0)
    {
      sb.append("Completion comments:");
      for(QuestCompletionComment comment : _completionComments)
      {
        sb.append(comment);
        sb.append(EndOfLine.NATIVE_EOL);
      }
    }
  }
}
