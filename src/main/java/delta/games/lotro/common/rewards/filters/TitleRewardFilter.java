package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.TitleReward;

/**
 * Filter for rewards that contain a title.
 * @author DAM
 */
public class TitleRewardFilter implements Filter<Rewards>
{
  private String _title;

  /**
   * Constructor.
   * @param title Title to select (may be <code>null</code>).
   */
  public TitleRewardFilter(String title)
  {
    _title=title;
  }

  /**
   * Get the title to use.
   * @return A title or <code>null</code>.
   */
  public String getTitle()
  {
    return _title;
  }

  /**
   * Set the title to select.
   * @param title Title to use, may be <code>null</code>.
   */
  public void setTitle(String title)
  {
    _title=title;
  }

  public boolean accept(Rewards rewards)
  {
    if (_title==null)
    {
      return true;
    }
    TitleReward[] titles=rewards.getTitles();
    if (titles!=null)
    {
      for(TitleReward title : titles)
      {
        if (_title.equals(title.getName()))
        {
          return true;
        }
      }
    }
    return false;
  }
}
