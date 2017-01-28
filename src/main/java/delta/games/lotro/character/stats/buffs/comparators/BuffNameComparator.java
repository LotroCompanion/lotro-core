package delta.games.lotro.character.stats.buffs.comparators;

import java.util.Comparator;

import delta.games.lotro.character.stats.buffs.Buff;

/**
 * Comparator for buffs, using their name.
 * @author DAM
 */
public class BuffNameComparator implements Comparator<Buff>
{
  public int compare(Buff o1, Buff o2)
  {
    String label1=o1.getLabel();
    String label2=o2.getLabel();
    if (label1!=null)
    {
      if (label2!=null)
      {
        return label1.compareTo(label2);
      }
      return 1;
    }
    return (label2!=null)?-1:0;
  }
}
