package delta.games.lotro.character.status.achievables.filter;

import delta.games.lotro.character.status.achievables.AchievableStatus;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.filters.DeedFilter;
import delta.games.lotro.lore.quests.Achievable;

/**
 * Filter for deed statuses.
 * @author DAM
 */
public class DeedStatusFilter extends AchievableStatusFilter
{
  private DeedFilter _deedFilter;

  /**
   * Constructor.
   */
  public DeedStatusFilter()
  {
    super();
    _deedFilter=new DeedFilter();
  }

  /**
   * Get the managed deed filter.
   * @return the managed deed filter.
   */
  public DeedFilter getDeedFilter()
  {
    return _deedFilter;
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
    if (achievable instanceof DeedDescription)
    {
      return _deedFilter.accept((DeedDescription)achievable);
    }
    return false;
  }
}
