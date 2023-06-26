package delta.games.lotro.common.rewards;

import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Title reward.
 * @author DAM
 */
public class TitleReward extends RewardElement
{
  private TitleDescription _title;

  /**
   * Constructor.
   * @param title Title.
   */
  public TitleReward(TitleDescription title)
  {
    _title=title;
  }

  /**
   * Get the title.
   * @return a title or <code>null</code> if not set.
   */
  public TitleDescription getTitle()
  {
    return _title;
  }

  /**
   * Get the name of the rewarded title.
   * @return a title name.
   */
  public String getName()
  {
    return (_title!=null)?_title.getName():"?";
  }

  @Override
  public String toString()
  {
    return getName();
  }
}
