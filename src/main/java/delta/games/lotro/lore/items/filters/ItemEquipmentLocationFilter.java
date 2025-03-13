package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;

/**
 * Filter items that use a given equipment location.
 * @author DAM
 */
public class ItemEquipmentLocationFilter implements ItemFilter
{
  private EquipmentLocation _location;

  /**
   * Constructor (accepts all locations, including "none").
   */
  public ItemEquipmentLocationFilter()
  {
    _location=null;
  }

  /**
   * Constructor.
   * @param location Location to select.
   */
  public ItemEquipmentLocationFilter(EquipmentLocation location)
  {
    _location=location;
  }

  /**
   * Get the selected location.
   * @return A location (may be <code>null</code> to ignore this filter.
   */
  public EquipmentLocation getLocation()
  {
    return _location;
  }

  /**
   * Set the location to select.
   * @param location Location to select.
   */
  public void setLocation(EquipmentLocation location)
  {
    _location=location;
  }

  @Override
  public boolean accept(Item item)
  {
    if (_location==null)
    {
      return true;
    }
    return _location==item.getEquipmentLocation();
  }
}
