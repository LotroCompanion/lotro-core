package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.items.Item;

/**
 * Hobby condition.
 * @author DAM
 */
public class HobbyCondition extends ObjectiveCondition
{
  private Item _item;

  /**
   * Constructor.
   */
  public HobbyCondition()
  {
    _item=null;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.HOBBY_ITEM;
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

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    sb.append(": Fish ");
    if (_item!=null)
    {
      sb.append(_item);
    }
    int count=getCount();
    if (count>1)
    {
      sb.append(" x").append(count);
    }
    return sb.toString();
  }
}
