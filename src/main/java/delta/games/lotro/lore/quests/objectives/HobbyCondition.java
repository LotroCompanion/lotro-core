package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.items.Item;

/**
 * Hobby condition.
 * @author DAM
 */
public class HobbyCondition extends ItemCondition
{
  /**
   * Constructor.
   */
  public HobbyCondition()
  {
    super();
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.HOBBY_ITEM;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    sb.append(": Catch ");
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
