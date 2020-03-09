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
    int tier1=factionLevel1!=null?factionLevel1.getTier():-100;
    int tier2=factionLevel2!=null?factionLevel2.getTier():-100;
    if (tier1>tier2) return 1;
    if (tier1<tier2) return -1;
    String name1=factionLevel1!=null?factionLevel1.getName():"";
    String name2=factionLevel2!=null?factionLevel2.getName():"";
    return name1.compareTo(name2);
  }
}
