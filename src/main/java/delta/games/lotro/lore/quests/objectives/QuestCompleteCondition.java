package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.utils.Proxy;

/**
 * Quest(s)/deed completion condition.
 * @author DAM
 */
public class QuestCompleteCondition extends ObjectiveCondition
{
  private Proxy<Achievable> _achievable;
  private String _questCategory;
  private int _completionCount;

  /**
   * Constructor.
   */
  public QuestCompleteCondition()
  {
    _achievable=null;
    _questCategory=null;
    _completionCount=1;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.QUEST_COMPLETE;
  }

  /**
   * Get the proxy to the targeted achievable.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<Achievable> getProxy()
  {
    return _achievable;
  }

  /**
   * Set the proxy to the targeted achievable.
   * @param proxy the proxy to set (may be <code>null</code>).
   */
  public void setProxy(Proxy<Achievable> proxy)
  {
    _achievable=proxy;
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
   * Set the quest completion count.
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
    if (_achievable!=null)
    {
      sb.append("quest/deed: ").append(_achievable);
    }
    else if (_questCategory!=null)
    {
      sb.append("quest in category: ").append(_questCategory);
    }
    return sb.toString();
  }
}
