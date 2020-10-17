package delta.games.lotro.lore.instances.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.instances.InstanceCategory;

/**
 * Comparator for instance categories using their name.
 * @author DAM
 */
public class InstanceCategoryNameComparator implements Comparator<InstanceCategory>
{
  @Override
  public int compare(InstanceCategory o1, InstanceCategory o2)
  {
    return o1.getName().compareTo(o2.getName());
  }
}
