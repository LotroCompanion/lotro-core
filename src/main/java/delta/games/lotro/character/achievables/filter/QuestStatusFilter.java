package delta.games.lotro.character.achievables.filter;

import delta.games.lotro.character.achievables.AchievableStatus;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.filter.QuestFilter;

/**
 * Filter for quest statuses.
 * @author DAM
 */
public class QuestStatusFilter extends AchievableStatusFilter
{
  private QuestFilter _questFilter;

  /**
   * Constructor.
   */
  public QuestStatusFilter()
  {
    super();
    _questFilter=new QuestFilter();
  }

  /**
   * Get the managed quest filter.
   * @return the managed quest filter.
   */
  public QuestFilter getQuestFilter()
  {
    return _questFilter;
  }

  @Override
  public boolean accept(AchievableStatus item)
  {
    boolean ok=super.accept(item);
    if (!ok)
    {
      return ok;
    }
    Achievable achievable=item.getAchievable();
    if (achievable instanceof QuestDescription)
    {
      return _questFilter.accept((QuestDescription)achievable);
    }
    return false;
  }
}
