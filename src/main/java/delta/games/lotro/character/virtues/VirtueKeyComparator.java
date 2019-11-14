package delta.games.lotro.character.virtues;

import java.util.Comparator;

/**
 * Comparator for virtues using their key.
 * @author DAM
 */
public class VirtueKeyComparator implements Comparator<VirtueDescription>
{
  @Override
  public int compare(VirtueDescription o1, VirtueDescription o2)
  {
    return o1.getPersistenceKey().compareTo(o2.getPersistenceKey());
  }
}
