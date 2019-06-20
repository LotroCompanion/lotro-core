package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

/**
 * Base class for item conditions.
 * @author DAM
 */
public abstract class ItemCondition extends ObjectiveCondition
{
  protected Proxy<Item> _item;
  protected int _count;

  /**
   * Constructor.
   */
  public ItemCondition()
  {
    _item=null;
    _count=1;
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
}
