package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.ItemsSetReward;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

/**
 * Filter for rewards that contain an item.
 * @author DAM
 */
public class ItemRewardFilter implements Filter<Rewards>
{
  private Integer _itemId;

  /**
   * Constructor.
   * @param itemId ID of item to select (may be <code>null</code>).
   */
  public ItemRewardFilter(Integer itemId)
  {
    _itemId=itemId;
  }

  /**
   * Get the item ID to use.
   * @return An item ID or <code>null</code>.
   */
  public Integer getItemId()
  {
    return _itemId;
  }

  /**
   * Set the item ID to select.
   * @param itemId Item ID to use, may be <code>null</code>.
   */
  public void setItemId(Integer itemId)
  {
    _itemId=itemId;
  }

  public boolean accept(Rewards rewards)
  {
    if (_itemId==null)
    {
      return true;
    }
    ItemsSetReward objects=rewards.getObjects();
    int nbObjects=objects.getNbObjectItems();
    for(int i=0;i<nbObjects;i++)
    {
      Proxy<Item> objectItem=objects.getItem(i);
      if (_itemId.intValue()==objectItem.getId())
      {
        return true;
      }
    }
    return false;
  }
}
