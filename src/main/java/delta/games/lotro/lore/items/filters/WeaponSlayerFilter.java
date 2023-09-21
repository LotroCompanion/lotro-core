package delta.games.lotro.lore.items.filters;

import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.WeaponSlayerInfo;

/**
 * Filter items with a given quality.
 * @author DAM
 */
public class WeaponSlayerFilter implements ItemFilter
{
  private Genus _genus;

  /**
   * Constructor.
   * @param genus Genus to search.
   */
  public WeaponSlayerFilter(Genus genus)
  {
    _genus=genus;
  }

  /**
   * Get the genus to select.
   * @return A genus or <code>null</code> to select all.
   */
  public Genus getGenus()
  {
    return _genus;
  }

  /**
   * Set the genus to search.
   * @param genus Genus to search.
   */
  public void setGenus(Genus genus)
  {
    _genus=genus;
  }

  @Override
  public boolean accept(Item item)
  {
    if (_genus!=null)
    {
      ItemDetailsManager mgr=item.getDetails();
      if (mgr==null)
      {
        return false;
      }
      WeaponSlayerInfo info=mgr.getFirstItemDetail(WeaponSlayerInfo.class);
      if (info==null)
      {
        return false;
      }
      return info.getGenus().contains(_genus);
    }
    return true;
  }
}
