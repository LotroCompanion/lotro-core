package delta.games.lotro.character.gear;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Contents of a single slot.
 * @author DAM
 */
public class GearSlotContents
{
  private GearSlot _slot;
  private ItemInstance<? extends Item> _item;

  /**
   * Constructor.
   * @param slot Targeted equipment slot.
   */
  public GearSlotContents(GearSlot slot)
  {
    _slot=slot;
    _item=null;
  }

  /**
   * Copy constructor.
   * @param source Source data.
   */
  public GearSlotContents(GearSlotContents source)
  {
    _slot=source._slot;
    if (source._item!=null)
    {
      _item=ItemFactory.cloneInstance(source._item);
    }
  }

  /**
   * Get the managed slot.
   * @return the managed slot.
   */
  public GearSlot getSlot()
  {
    return _slot;
  }

  /**
   * Get the item instance in this slot.
   * @return an item instance or <code>null</code>.
   */
  public ItemInstance<? extends Item> getItem()
  {
    return _item;
  }

  /**
   * Set the item instance in this slot.
   * @param item Item instance to set.
   */
  public void setItem(ItemInstance<? extends Item> item)
  {
    _item=item;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Slot ").append(_slot).append(": ");
    if (_item!=null)
    {
      sb.append(" item=[").append(_item).append(']');
    }
    return sb.toString().trim();
  }
}
