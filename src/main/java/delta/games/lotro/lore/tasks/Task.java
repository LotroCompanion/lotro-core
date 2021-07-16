package delta.games.lotro.lore.tasks;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Task definition.
 * @author DAM
 */
public class Task
{
  private QuestDescription _quest;
  private Item _item;
  private int _itemCount;

  /**
   * Constructor.
   * @param quest Quest definition.
   */
  public Task(QuestDescription quest)
  {
    _quest=quest;
  }

  /**
   * Get the associated quest.
   * @return a quest.
   */
  public QuestDescription getQuest()
  {
    return _quest;
  }

  /**
   * Get the required item.
   * @return an item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the required item count.
   * @return a count.
   */
  public int getItemCount()
  {
    return _itemCount;
  }

  /**
   * Set the required item.
   * @param item Item to set.
   * @param count Count to set.
   */
  public void setRequiredItems(Item item, int count)
  {
    _item=item;
    _itemCount=count;
  }
}
