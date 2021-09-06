package delta.games.lotro.lore.items.filters;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;

/**
 * Filter items that can go in a given equipment location.
 * @author DAM
 */
public class ItemEquipmentLocationFilter implements ItemFilter
{
  private Set<EquipmentLocation> _locations;

  /**
   * Constructor (accepts all locations, including "no location").
   */
  public ItemEquipmentLocationFilter()
  {
    _locations=new HashSet<EquipmentLocation>();
    selectAll();
  }

  /**
   * Constructor.
   * @param locations Locations to select.
   */
  public ItemEquipmentLocationFilter(Set<EquipmentLocation> locations)
  {
    _locations=locations;
  }

  /**
   * Select all locations.
   */
  public void selectAll()
  {
    _locations.clear();
    _locations.add(null);
    for(EquipmentLocation location : EquipmentLocation.getAll())
    {
      _locations.add(location);
    }
  }

  /**
   * Get the selected locations.
   * @return A possibly empty but never <code>null</code> set of location.
   */
  public Set<EquipmentLocation> getSelectedLocations()
  {
    return _locations;
  }

  /**
   * Set the locations to select.
   * @param locations Locations to select.
   */
  public void setLocations(Set<EquipmentLocation> locations)
  {
    _locations=locations;
  }

  @Override
  public boolean accept(Item item)
  {
    return _locations.contains(item.getEquipmentLocation());
  }
}
