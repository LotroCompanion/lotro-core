package delta.games.lotro.character.storage.location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.character.storage.location.comparators.LocationComparator;

/**
 * Compound storage location.
 * @author DAM
 */
public class CompoundStorageLocation extends StorageLocation
{
  private List<StorageLocation> _locations;

  /**
   * Constructor.
   */
  public CompoundStorageLocation()
  {
    _locations=new ArrayList<StorageLocation>();
  }

  /**
   * Sort locations.
   */
  public void sortLocations()
  {
    Collections.sort(_locations,new LocationComparator());
  }

  /**
   * Add a location.
   * @param location Location to add.
   */
  public void add(StorageLocation location)
  {
    if (!_locations.contains(location))
    {
      _locations.add(location);
    }
  }

  @Override
  public String getLabel()
  {
    StringBuilder sb=new StringBuilder();
    for(StorageLocation location : _locations)
    {
      if (sb.length()>0)
      {
        sb.append(" / ");
      }
      sb.append(location.getLabel());
    }
    return sb.toString();
  }

  /**
   * Get the number of storage locations.
   * @return a count of storage locations.
   */
  public int getStorageLocationsCount()
  {
    return _locations.size();
  }

  /**
   * Get the storage location at the specified index.
   * @param index Index of the storage location to get.
   * @return A storage location.
   */
  public StorageLocation getStorageLocationAt(int index)
  {
    return _locations.get(index);
  }

  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }
    if (!(object instanceof CompoundStorageLocation))
    {
      return false;
    }
    CompoundStorageLocation other=(CompoundStorageLocation)object;
    int nbItems=_locations.size();
    if (nbItems != other._locations.size())
    {
      return false;
    }
    for(StorageLocation location : _locations)
    {
      if (!other._locations.contains(location))
      {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    for(StorageLocation location : _locations)
    {
      result = prime * result + location.hashCode();
    }
    return result;
  }
}
