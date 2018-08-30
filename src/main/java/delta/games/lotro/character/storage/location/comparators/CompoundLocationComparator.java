package delta.games.lotro.character.storage.location.comparators;

import java.util.Comparator;

import delta.games.lotro.character.storage.location.CompoundStorageLocation;

/**
 * Comparator for compound locations.
 * @author DAM
 */
public class CompoundLocationComparator implements Comparator<CompoundStorageLocation>
{
  private LocationComparator _comparator;

  /**
   * Constructor.
   * @param comparator Comparator to use.
   */
  public CompoundLocationComparator(LocationComparator comparator)
  {
    _comparator=comparator;
  }

  @Override
  public int compare(CompoundStorageLocation o1, CompoundStorageLocation o2)
  {
    int size1=o1.getStorageLocationsCount();
    int size2=o2.getStorageLocationsCount();
    int min=Math.min(size1,size2);
    for(int i=0;i<min;i++)
    {
      int ret=_comparator.compare(o1.getStorageLocationAt(i),o2.getStorageLocationAt(i));
      if (ret!=0)
      {
        return ret;
      }
    }
    return Integer.compare(size1,size2);
  }
}
