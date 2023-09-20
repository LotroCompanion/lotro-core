package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.lore.items.details.WeaponSlayerInfo;

/**
 * Comparator for weapon slayer info.
 * @author DAM
 */
public class WeaponSlayerInfoComparator implements Comparator<WeaponSlayerInfo>
{
  @Override
  public int compare(WeaponSlayerInfo o1, WeaponSlayerInfo o2)
  {
    if (o1==null)
    {
      return (o2==null)?0:-1;
    }
    if (o2==null)
    {
      return 1;
    }
    int s1=Math.round(o1.getSlayer());
    int s2=Math.round(o2.getSlayer());
    int ret=Integer.compare(s1,s2);
    if (ret!=0)
    {
      return ret;
    }
    Genus g1=o1.getGenus().get(0);
    Genus g2=o2.getGenus().get(0);
    return g1.getLabel().compareTo(g2.getLabel());
  }
}
