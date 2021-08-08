package delta.games.lotro.lore.reputation;

import java.util.Comparator;

/**
 * Comparator for faction names.
 * @author DAM
 */
public class FactionNameComparator implements Comparator<Faction>
{
  @Override
  public int compare(Faction faction1, Faction faction2)
  {
    String name1=faction1!=null?faction1.getName():"";
    String name2=faction2!=null?faction2.getName():"";
    return name1.compareTo(name2);
  }
}
