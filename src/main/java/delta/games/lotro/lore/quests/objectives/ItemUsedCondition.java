package delta.games.lotro.lore.quests.objectives;

/**
 * Inventory item condition.
 * @author DAM
 */
public class ItemUsedCondition extends ItemCondition
{
  @Override
  public ConditionType getType()
  {
    return ConditionType.ITEM_USED;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    if (_item!=null)
    {
      sb.append(": Use item: ").append(_item);
      if (_count>1)
      {
        sb.append(_count).append(" x");
      }
    }
    return sb.toString();
  }
}
