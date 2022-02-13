package delta.games.lotro.character.storage.location.comparators;

import java.util.Comparator;

import delta.games.lotro.character.storage.location.StorageLocation;
import delta.games.lotro.character.storage.location.StorageLocation.LocationType;
import delta.games.lotro.common.owner.Owner;
import delta.games.lotro.common.owner.comparators.OwnerComparator;

/**
 * Comparator for character owners.
 * @author DAM
 */
public class LocationComparator implements Comparator<StorageLocation>
{
  private OwnerComparator _ownerComparator=new OwnerComparator();

  @Override
  public int compare(StorageLocation o1, StorageLocation o2)
  {
    Owner owner1=o1.getOwner();
    Owner owner2=o2.getOwner();
    int ret=_ownerComparator.compare(owner1,owner2);
    if (ret==0)
    {
      LocationType type1=o1.getLocationType();
      LocationType type2=o2.getLocationType();
      ret=type1.ordinal()-type2.ordinal();
      if (ret==0)
      {
        ret=o1.getChestName().compareTo(o2.getChestName());
      }
    }
    return ret;
  }
}
