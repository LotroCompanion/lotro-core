package delta.games.lotro.lore.quests.objectives;

import java.util.Comparator;

/**
 * Comparator for objective conditions, using their index.
 * @author DAM
 */
public class ObjectiveIndexComparator implements Comparator<Objective>
{
  @Override
  public int compare(Objective o1, Objective o2)
  {
    int index1=o1.getIndex();
    int index2=o2.getIndex();
    return Integer.compare(index1,index2);
  }
}
