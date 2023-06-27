package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.items.Item;

/**
 * Base class for item conditions.
 * @author DAM
 */
public abstract class ItemCondition extends ObjectiveCondition
{
  protected Item _item;

  /**
   * Constructor.
   */
  protected ItemCondition()
  {
    _item=null;
  }

  /**
   * Get the targeted item.
   * @return an item or <code>null</code>.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Set the targeted item.
   * @param item the item to set (may be <code>null</code>).
   */
  public void setItem(Item item)
  {
    _item=item;
  }
}
