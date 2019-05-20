package delta.games.lotro.lore.quests.objectives;

import java.util.Comparator;

/**
 * Comparator for objectives, using their index.
 * @author DAM
 */
public class ObjectiveConditionComparator implements Comparator<ObjectiveCondition>
{
  @Override
  public int compare(ObjectiveCondition o1, ObjectiveCondition o2)
  {
    int index1=o1.getIndex();
    int index2=o2.getIndex();
    return Integer.compare(index1,index2);
  }
}
