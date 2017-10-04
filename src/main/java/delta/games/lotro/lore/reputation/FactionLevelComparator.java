package delta.games.lotro.lore.reputation;

import java.util.Comparator;

/**
 * Comparator for faction levels.
 * @author DAM
 */
public class FactionLevelComparator implements Comparator<FactionLevel>
{
  public int compare(FactionLevel factionLevel1, FactionLevel factionLevel2)
  {
    int level1=factionLevel1.getValue();
    int level2=factionLevel2.getValue();
    if (level1>level2) return 1;
    if (level1<level2) return -1;
    String name1=factionLevel1.getName();
    String name2=factionLevel2.getName();
    return name1.compareTo(name2);
  }
}
