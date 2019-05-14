package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Repeatability;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Filter for quests with a given repeatability.
 * @author DAM
 */
public class RepeatabilityFilter implements Filter<QuestDescription>
{
  private Repeatability _repeatability;

  /**
   * Constructor.
   * @param repeatability Repeatability to select (may be <code>null</code>).
   */
  public RepeatabilityFilter(Repeatability repeatability)
  {
    _repeatability=repeatability;
  }

  /**
   * Get the repeatability to use.
   * @return A repeatability or <code>null</code>.
   */
  public Repeatability getRepeatability()
  {
    return _repeatability;
  }

  /**
   * Set the repeatability to select.
   * @param repeatability Repeatability to use, may be <code>null</code>.
   */
  public void setRepeatability(Repeatability repeatability)
  {
    _repeatability=repeatability;
  }

  @Override
  public boolean accept(QuestDescription quest)
  {
    if (_repeatability==null)
    {
      return true;
    }
    Repeatability repeatability=quest.getRepeatability();
    return (_repeatability==repeatability);
  }
}
