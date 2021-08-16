package delta.games.lotro.lore.tasks.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.tasks.Task;

/**
 * Comparator for tasks using the item name.
 * @author DAM
 */
public class TaskItemNameComparator implements Comparator<Task>
{
  @Override
  public int compare(Task o1, Task o2)
  {
    Item item1=o1.getItem();
    Item item2=o2.getItem();
    String itemName1=item1.getName();
    if (itemName1==null) itemName1="";
    String itemName2=item2.getName();
    if (itemName2==null) itemName2="";
    return itemName1.compareTo(itemName2);
  }
}
