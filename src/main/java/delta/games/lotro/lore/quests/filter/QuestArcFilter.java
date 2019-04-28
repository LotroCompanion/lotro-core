package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Filter for quests of a given quest arc.
 * @author DAM
 */
public class QuestArcFilter implements Filter<QuestDescription>
{
  private String _questArc;

  /**
   * Constructor.
   * @param questArc Quest arc to select (may be <code>null</code>).
   */
  public QuestArcFilter(String questArc)
  {
    _questArc=questArc;
  }

  /**
   * Get the quest arc to use.
   * @return A quest arc or <code>null</code>.
   */
  public String getQuestArc()
  {
    return _questArc;
  }

  /**
   * Set the quest arc to select.
   * @param questArc Quest arc to use, may be <code>null</code>.
   */
  public void setQuestArc(String questArc)
  {
    _questArc=questArc;
  }

  public boolean accept(QuestDescription quest)
  {
    if (_questArc==null)
    {
      return true;
    }
    String questArc=quest.getQuestArc();
    if (_questArc.equals(questArc))
    {
      return true;
    }
    return false;
  }
}
