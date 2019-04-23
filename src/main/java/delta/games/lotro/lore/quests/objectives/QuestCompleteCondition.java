package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Quest(s)/deed completion condition.
 * @author DAM
 */
public class QuestCompleteCondition extends ObjectiveCondition
{
  private Proxy<QuestDescription> _quest;
  private Proxy<DeedDescription> _deed;
  private String _questCategory;
  private int _completionCount;

  /**
   * Constructor.
   */
  public QuestCompleteCondition()
  {
    _quest=null;
    _deed=null;
    _questCategory=null;
    _completionCount=1;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.QUEST_COMPLETE;
  }

  /**
   * Get the targeted quest.
   * @return a quest proxy or <code>null</code>.
   */
  public Proxy<QuestDescription> getQuest()
  {
    return _quest;
  }

  /**
   * Set the targeted quest.
   * @param quest the quest proxy to set (may be <code>null</code>).
   */
  public void setQuest(Proxy<QuestDescription> quest)
  {
    _quest=quest;
  }

  /**
   * Get the targeted deed.
   * @return a deed proxy or <code>null</code>.
   */
  public Proxy<DeedDescription> getDeed()
  {
    return _deed;
  }

  /**
   * Set the targeted deed.
   * @param deed the deed proxy to set (may be <code>null</code>).
   */
  public void setDeed(Proxy<DeedDescription> deed)
  {
    _deed=deed;
  }

  /**
   * Get the quest category.
   * @return a quest category or <code>null/code>.
   */
  public String getQuestCategory()
  {
    return _questCategory;
  }

  /**
   * Set the quest category.
   * @param questCategory category to set.
   */
  public void setQuestCategory(String questCategory)
  {
    _questCategory=questCategory;
  }

  /**
   * Get the quest/deed completion count.
   * @return a count.
   */
  public int getCompletionCount()
  {
    return _completionCount;
  }

  /**
   * Set the quest/deed completion count.
   * @param completionCount the completion count to set.
   */
  public void setCompletionCount(int completionCount)
  {
    _completionCount=completionCount;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    sb.append(": Complete ");
    if (_completionCount>1)
    {
      sb.append(_completionCount).append("x ");
    }
    if (_quest!=null)
    {
      sb.append("quest: ").append(_quest);
    }
    else if (_deed!=null)
    {
      sb.append("deed: ").append(_deed);
    }
    else if (_questCategory!=null)
    {
      sb.append("quest in category: ").append(_questCategory);
    }
    return sb.toString();
  }
}
