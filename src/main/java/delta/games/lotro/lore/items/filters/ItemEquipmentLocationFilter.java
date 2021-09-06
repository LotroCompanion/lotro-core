package delta.games.lotro.lore.items.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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

  /**
   * Get the state of this filter as a list of strings.
   * @return A list of strings.
   */
  public List<String> asString()
  {
    List<String> ret=new ArrayList<String>();
    for(EquipmentLocation location : _locations)
    {
      if (location!=null)
      {
        ret.add(location.getKey());
      }
      else
      {
        ret.add("null");
      }
    }
    Collections.sort(ret);
    return ret;
  }

  /**
   * Set the state of this filter from the given list of string.
   * @param input Input list.
   */
  public void loadFromString(List<String> input)
  {
    Set<EquipmentLocation> locations=new HashSet<EquipmentLocation>();
    for(String locationKey : input)
    {
      if ("null".equals(locationKey))
      {
        locations.add(null);
      }
      else
      {
        locations.add(EquipmentLocation.getByKey(locationKey));
      }
    }
    setLocations(locations);
  }
}
