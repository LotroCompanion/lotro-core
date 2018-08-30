package delta.games.lotro.character.storage.location.comparators;

import java.util.Comparator;

import delta.games.lotro.character.storage.location.CompoundStorageLocation;
import delta.games.lotro.character.storage.location.SimpleStorageLocation;
import delta.games.lotro.character.storage.location.StorageLocation;

/**
 * Comparator for all storage location types.
 * @author DAM
 */
public class LocationComparator implements Comparator<StorageLocation>
{
  private SimpleLocationComparator _simpleComparator=new SimpleLocationComparator();
  private CompoundLocationComparator _compoundComparator=new CompoundLocationComparator(this);

  @Override
  public int compare(StorageLocation o1, StorageLocation o2)
  {
    int index1=getLocationIndex(o1);
    int index2=getLocationIndex(o2);
    if (index1!=index2)
    {
      return index1-index2;
    }
    if (index1==1)
    {
      return _simpleComparator.compare((SimpleStorageLocation)o1,(SimpleStorageLocation)o2);
    }
    if (index1==2)
    {
      return _compoundComparator.compare((CompoundStorageLocation)o1,(CompoundStorageLocation)o2);
    }
    return 0;
  }

  private int getLocationIndex(StorageLocation location)
  {
    if (location instanceof SimpleStorageLocation) return 1;
    if (location instanceof CompoundStorageLocation) return 2;
    return 3;
  }
}
