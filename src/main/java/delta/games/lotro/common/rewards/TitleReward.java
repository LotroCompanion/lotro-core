package delta.games.lotro.common.rewards;

import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Title reward.
 * @author DAM
 */
public class TitleReward extends RewardElement
{
  private Proxy<TitleDescription> _title;

  /**
   * Constructor.
   * @param titleProxy Title proxy.
   */
  public TitleReward(Proxy<TitleDescription> titleProxy)
  {
    _title=titleProxy;
  }

  /**
   * Get the title proxy.
   * @return a title proxy or <code>null</code> if not set.
   */
  public Proxy<TitleDescription> getTitleProxy()
  {
    return _title;
  }

  /**
   * Get the name of the rewarded title.
   * @return a title name.
   */
  public String getName()
  {
    return _title.getName();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_title!=null)
    {
      sb.append(_title.getName());
    }
    else
    {
      sb.append('?');
    }
    return sb.toString();
  }
}
