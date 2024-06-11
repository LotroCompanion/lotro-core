package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.common.enums.QuestCategory;
import delta.games.lotro.common.enums.QuestScope;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.utils.Proxy;

/**
 * Quest(s)/deed completion condition.
 * @author DAM
 */
public class QuestCompleteCondition extends ObjectiveCondition
{
  private Proxy<Achievable> _achievable;
  private QuestCategory _questCategory;
  private QuestScope _questScope;

  /**
   * Constructor.
   */
  public QuestCompleteCondition()
  {
    _achievable=null;
    _questCategory=null;
    _questScope=null;
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
  public QuestCategory getQuestCategory()
  {
    return _questCategory;
  }

  /**
   * Set the quest category.
   * @param questCategory category to set.
   */
  public void setQuestCategory(QuestCategory questCategory)
  {
    _questCategory=questCategory;
  }

  /**
   * Get the quest scope.
   * @return a quest scope or <code>null/code>.
   */
  public QuestScope getQuestScope()
  {
    return _questScope;
  }

  /**
   * Set the quest scope.
   * @param questScope scope to set.
   */
  public void setQuestScope(QuestScope questScope)
  {
    _questScope=questScope;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    sb.append(": Complete ");
    int completionCount=getCount();
    if (completionCount>1)
    {
      sb.append(completionCount).append("x ");
    }
    if (_achievable!=null)
    {
      sb.append("quest/deed: ").append(_achievable);
    }
    else if (_questCategory!=null)
    {
      sb.append("quest in category: ").append(_questCategory);
    }
    if (_questScope!=null)
    {
      sb.append(", scope=").append(_questScope);
    }
    return sb.toString();
  }
}
