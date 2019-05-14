package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Filter for quests that are session-play, or that are NOT session-play.
 * @author DAM
 */
public class SessionPlayQuestFilter implements Filter<QuestDescription>
{
  private Boolean _isSessionPlay;

  /**
   * Constructor.
   * @param isSessionPlay Indicates if this filter shall select quests
   * that are session-play, or not (<code>null</code> means no filter).
   */
  public SessionPlayQuestFilter(Boolean isSessionPlay)
  {
    _isSessionPlay=isSessionPlay;
  }

  /**
   * Get the value of the 'is session-play' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getIsSessionPlayFlag()
  {
    return _isSessionPlay;
  }

  /**
   * Set the value of the 'is session-play' flag.
   * @param isSessionPlay Flag to set, may be <code>null</code>.
   */
  public void setIsSessionPlayFlag(Boolean isSessionPlay)
  {
    _isSessionPlay=isSessionPlay;
  }

  @Override
  public boolean accept(QuestDescription quest)
  {
    if (_isSessionPlay==null)
    {
      return true;
    }
    boolean sessionPlay=quest.isSessionPlay();
    return (_isSessionPlay.booleanValue()==sessionPlay);
  }
}
