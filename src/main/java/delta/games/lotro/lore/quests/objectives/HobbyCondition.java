package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

/**
 * Hobby condition.
 * @author DAM
 */
public class HobbyCondition extends ObjectiveCondition
{
  private Proxy<Item> _item;
  private int _count;

  /**
   * Constructor.
   */
  public HobbyCondition()
  {
    _item=null;
    _count=1;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.HOBBY_ITEM;
  }

  /**
   * Get the proxy to the targeted item.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<Item> getProxy()
  {
    return _item;
  }

  /**
   * Set the proxy to the targeted item.
   * @param proxy the proxy to set (may be <code>null</code>).
   */
  public void setProxy(Proxy<Item> proxy)
  {
    _item=proxy;
  }

  /**
   * Get the count.
   * @return a count.
   */
  public int getCount()
  {
    return _count;
  }

  /**
   * Set the count.
   * @param count the count to set.
   */
  public void setCount(int count)
  {
    _count=count;
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
    if (_count>1)
    {
      sb.append(" x").append(_count);
    }
    return sb.toString();
  }
}
