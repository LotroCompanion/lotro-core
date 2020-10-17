package delta.games.lotro.lore.instances.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.instances.PrivateEncounter;

/**
 * Comparator for private encounters using their name.
 * @author DAM
 */
public class PrivateEncounterNameComparator implements Comparator<PrivateEncounter>
{
  @Override
  public int compare(PrivateEncounter o1, PrivateEncounter o2)
  {
    return o1.getName().compareTo(o2.getName());
  }
}
