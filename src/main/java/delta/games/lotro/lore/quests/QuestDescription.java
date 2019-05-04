package delta.games.lotro.lore.quests;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Repeatability;
import delta.games.lotro.common.Size;
import delta.games.lotro.utils.Proxy;

/**
 * LOTRO quest description.
 * @author DAM
 */
public class QuestDescription extends Achievable
{
  /**
   * Faction.
   * @author DAM
   */
  public enum FACTION
  {
    /**
     * Free peoples.
     */
    FREE_PEOPLES,
    /**
     * Monster play.
     */
    MONSTER_PLAY
  }

  /**
   * Scope.
   */
  private String _scope;
  /**
   * Quest arc. Can be empty be not <code>null</code>.
   */
  private String _questArc;
  /**
   * Recommended size: solo, small felloship, fellowship or raid.
   */
  private Size _size;
  /**
   * Free Peoples or Monster Play.
   */
  private FACTION _faction;
  /**
   * Repeatability (never null).
   */
  private Repeatability _repeatability;

  // Flags
  private boolean _instanced;
  private boolean _shareable;
  private boolean _sessionPlay;
  private boolean _autoBestowed;

  // TODO Structured!
  private String _bestower;
  private String _bestowerText;
  // Links
  private List<Proxy<Achievable>> _prerequisiteAchievables;
  private Proxy<Achievable> _nextQuest;

  /**
   * Constructor.
   */
  public QuestDescription()
  {
    super();
    _scope="";
    _questArc="";
    _size=Size.SOLO;
    _faction=FACTION.FREE_PEOPLES;
    _repeatability=Repeatability.NOT_REPEATABLE;
    _instanced=false;
    _shareable=true;
    _sessionPlay=false;
    _autoBestowed=false;
    _bestower="";
    _bestowerText="";
    _prerequisiteAchievables=new ArrayList<Proxy<Achievable>>();
    _nextQuest=null;
  }

  /**
   * Get the scope of this quest.
   * @return the scope of this quest.
   */
  public String getQuestScope()
  {
    return _scope;
  }

  /**
   * Set the scope of this quest.
   * @param scope the scope to set.
   */
  public void setQuestScope(String scope)
  {
    if (scope==null) scope="";
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
   * Get the quest faction.
   * @return the quest faction.
   */
  public FACTION getFaction()
  {
    return _faction;
  }

  /**
   * Set the quest faction.
   * @param faction the faction to set.
   */
  public void setFaction(FACTION faction)
  {
    _faction=faction;
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
   * Get the bestower of this quest.
   * @return the bestower of this quest.
   */
  public String getBestower()
  {
    return _bestower;
  }

  /**
   * Set the bestower of this quest.
   * @param bestower the bestower to set.
   */
  public void setBestower(String bestower)
  {
    if (bestower==null) bestower="";
    _bestower=bestower;
  }

  /**
   * Get the bestower text of this quest.
   * @return the bestower text of this quest.
   */
  public String getBestowerText()
  {
    return _bestowerText;
  }

  /**
   * Set the bestower text of this quest.
   * @param bestowerText the bestower text to set.
   */
  public void setBestowerText(String bestowerText)
  {
    if (bestowerText==null) bestowerText="";
    _bestowerText=bestowerText;
  }

  /**
   * Get the list of the 'pre-requisite' quests for this quest. 
   * @return a possibly empty list of quest proxies.
   */
  public List<Proxy<Achievable>> getPrerequisites()
  {
    return _prerequisiteAchievables;
  }

  /**
   * Add a 'pre-requisite' achievable.
   * @param prerequisite proxy to add as a 'pre-requisite'.
   */
  public void addPrerequisite(Proxy<Achievable> prerequisite)
  {
    _prerequisiteAchievables.add(prerequisite);
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
    if (_faction!=FACTION.FREE_PEOPLES)
    {
      sb.append(" (");
      sb.append(_faction);
      sb.append(')');
    }
    if (_repeatability!=Repeatability.NOT_REPEATABLE)
    {
      sb.append(" (").append(_repeatability).append(")");
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
    super.dumpOtherLines(sb);
    if (_scope.length()>0)
    {
      sb.append("Scope: ").append(_scope).append(EndOfLine.NATIVE_EOL);
    }
    if (_questArc.length()>0)
    {
      sb.append("Arc: ").append(_questArc).append(EndOfLine.NATIVE_EOL);
    }
    if (_prerequisiteAchievables.size()>0)
    {
      sb.append("Prerequisites: ").append(_prerequisiteAchievables).append(EndOfLine.NATIVE_EOL);
    }
    if (_nextQuest!=null)
    {
      sb.append("Next quest: ").append(_nextQuest).append(EndOfLine.NATIVE_EOL);
    }
    sb.append("Bestower: ").append(_bestower).append(EndOfLine.NATIVE_EOL);
    sb.append("Bestower text: ").append(_bestowerText).append(EndOfLine.NATIVE_EOL);
    return sb.toString();
  }
}
