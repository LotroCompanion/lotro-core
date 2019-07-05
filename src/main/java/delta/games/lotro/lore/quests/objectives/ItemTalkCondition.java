package delta.games.lotro.lore.quests.objectives;

/**
 * External inventory item condition.
 * @author DAM
 */
public class ItemTalkCondition extends ItemCondition
{
  @Override
  public ConditionType getType()
  {
    return ConditionType.ITEM_TALK;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    if (_item!=null)
    {
      sb.append(": Obtain item: ").append(_item);
      if (_count>1)
      {
        sb.append(_count).append(" x");
      }
    }
    return sb.toString();
  }
}
