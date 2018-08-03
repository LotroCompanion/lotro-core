package delta.games.lotro.character.storage.location;

import java.util.ArrayList;
import java.util.List;

/**
 * Compound storage location.
 * @author DAM
 */
public class CompoundStorage extends StorageLocation
{
  private List<StorageLocation> _locations;

  /**
   * Constructor.
   */
  public CompoundStorage()
  {
    _locations=new ArrayList<StorageLocation>();
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

  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }
    if (!(object instanceof CompoundStorage))
    {
      return false;
    }
    CompoundStorage other=(CompoundStorage)object;
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
}
