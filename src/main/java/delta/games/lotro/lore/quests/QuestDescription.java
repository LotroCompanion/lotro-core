package delta.games.lotro.lore.quests;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Size;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.utils.Proxy;

/**
 * LOTRO quest description.
 * @author DAM
 */
public class QuestDescription implements Identifiable
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

  private int _identifier;
  /**
   * Quest name. Can be empty be not <code>null</code>.
   */
  private String _name;
  private String _category;
  private String _scope;
  private String _questArc;

  // Requirements
  private UsageRequirement _requirement;

  private Size _size;
  private FACTION _faction;
  // TODO Use int? cases are: false, xN=3, repeatable
  private boolean _repeatable;

  // Flags
  private boolean _instanced;
  private boolean _shareable;
  private boolean _sessionPlay;
  private boolean _autoBestowed;

  private String _description;
  // TODO Structured!
  private String _bestower;
  private String _bestowerText;
  // TODO Structured!
  private String _objectives;
  private List<Proxy<QuestDescription>> _prerequisiteQuests;
  private Proxy<QuestDescription> _nextQuest;
  private Rewards _rewards;

  /**
   * Constructor.
   */
  public QuestDescription()
  {
    _identifier=0;
    _name="";
    _category="";
    _scope="";
    _questArc="";
    _requirement=new UsageRequirement();
    _size=Size.SOLO;
    _faction=FACTION.FREE_PEOPLES;
    _repeatable=false;
    _instanced=false;
    _shareable=true;
    _sessionPlay=false;
    _autoBestowed=false;
    _description="";
    _bestower="";
    _bestowerText="";
    _objectives="";
    _prerequisiteQuests=new ArrayList<Proxy<QuestDescription>>();
    _nextQuest=null;
    _rewards=new Rewards();
  }

  /**
   * Get the identifier of this quest.
   * @return the identifier of this quest.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this quest.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the name of this quest.
   * @return the name of this quest.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this quest.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    if (name==null) name="";
    _name=name;
  }

  /**
   * Get the category of this quest.
   * @return the category of this quest.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category of this quest. 
   * @param category the category to set.
   */
  public void setCategory(String category)
  {
    _category=category;
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
    _questArc=questArc;
  }

  /**
   * Get the minimum level for this quest.
   * @return the minimumLevel for this quest.
   */
  public Integer getMinimumLevel()
  {
    return _requirement.getMinLevel();
  }

  /**
   * Set the minimum level for this quest.
   * @param minimumLevel the minimum level to set.
   */
  public void setMinimumLevel(Integer minimumLevel)
  {
    _requirement.setMinLevel(minimumLevel);
  }

  /**
   * Get the maximum level for this quest.
   * @return the maximumLevel for this quest.
   */
  public Integer getMaximumLevel()
  {
    return _requirement.getMaxLevel();
  }

  /**
   * Set the maximum level for this quest.
   * @param maximumLevel the maxiimum level to set.
   */
  public void setMaximumLevel(Integer maximumLevel)
  {
    _requirement.setMaxLevel(maximumLevel);
  }

  /**
   * Get the usage requirement.
   * @return the usage requirement.
   */
  public UsageRequirement getUsageRequirement()
  {
    return _requirement;
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
   * Indicates if this quest is repeatable or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isRepeatable()
  {
    return _repeatable;
  }

  /**
   * Set the 'repeatable' flag.
   * @param repeatable value to set.
   */
  public void setRepeatable(boolean repeatable)
  {
    _repeatable=repeatable;
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
   * Get the description of this quest.
   * @return the description of this quest.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this quest.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    _description=description;
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
    _bestowerText=bestowerText;
  }

  /**
   * Get the objectives of this quest.
   * @return the objectives of this quest.
   */
  public String getObjectives()
  {
    return _objectives;
  }

  /**
   * Set the objectives of this quest.
   * @param objectives the objectives to set.
   */
  public void setObjectives(String objectives)
  {
    _objectives=objectives;
  }

  /**
   * Get the list of the 'pre-requisite' quests for this quest. 
   * @return a possibly empty list of quest proxies.
   */
  public List<Proxy<QuestDescription>> getPrerequisiteQuests()
  {
    return _prerequisiteQuests;
  }

  /**
   * Add a 'pre-requisite' quest.
   * @param prerequisiteQuest quest proxy to add as a 'pre-requisite' quest.
   */
  public void addPrerequisiteQuest(Proxy<QuestDescription> prerequisiteQuest)
  {
    _prerequisiteQuests.add(prerequisiteQuest);
  }

  /**
   * Get the 'next' quest for this quest. 
   * @return a proxy or <code>null</code>.
   */
  public Proxy<QuestDescription> getNextQuest()
  {
    return _nextQuest;
  }

  /**
   * Set the 'next' quest.
   * @param nextQuest proxy to set as a 'next' quest.
   */
  public void setNextQuest(Proxy<QuestDescription> nextQuest)
  {
    _nextQuest=nextQuest;
  }

  /**
   * Get the rewards for this quest.
   * @return the rewards.
   */
  public Rewards getQuestRewards()
  {
    return _rewards;
  }

  /**
   * Dump the contents of this quest as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Name: ").append(_name);
    if (_identifier!=0)
    {
      sb.append(" (");
      sb.append(_identifier);
      sb.append(')');
    }
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
    if (_repeatable)
    {
      sb.append(" (repeatable)");
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
    if (_category.length()>0)
    {
      sb.append("Category: ").append(_category).append(EndOfLine.NATIVE_EOL);
    }
    if (_scope.length()>0)
    {
      sb.append("Scope: ").append(_scope).append(EndOfLine.NATIVE_EOL);
    }
    if (_questArc.length()>0)
    {
      sb.append("Arc: ").append(_questArc).append(EndOfLine.NATIVE_EOL);
    }
    if (!_requirement.isEmpty())
    {
      sb.append("Requirements: ").append(_requirement).append(EndOfLine.NATIVE_EOL);
    }
    if (_prerequisiteQuests.size()>0)
    {
      sb.append("Prerequisites: ").append(_prerequisiteQuests).append(EndOfLine.NATIVE_EOL);
    }
    if (_nextQuest!=null)
    {
      sb.append("Next quest: ").append(_nextQuest).append(EndOfLine.NATIVE_EOL);
    }
    sb.append("Rewards: ").append(_rewards).append(EndOfLine.NATIVE_EOL);
    sb.append("Description: ").append(_description).append(EndOfLine.NATIVE_EOL);
    sb.append("Bestower: ").append(_bestower).append(EndOfLine.NATIVE_EOL);
    sb.append("Bestower text: ").append(_bestowerText).append(EndOfLine.NATIVE_EOL);
    sb.append("Objectives: ").append(_objectives).append(EndOfLine.NATIVE_EOL);
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
