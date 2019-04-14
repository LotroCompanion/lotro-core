package delta.games.lotro.common.rewards.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.ItemReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
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
    return accept(rewards.getRewardElements());
  }

  private boolean accept(List<RewardElement> elements)
  {
    for(RewardElement rewardElement : elements)
    {
      if (rewardElement instanceof ItemReward)
      {
        ItemReward titleReward=(ItemReward)rewardElement;
        if (accept(titleReward))
        {
          return true;
        }
      }
      else if (rewardElement instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectable=(SelectableRewardElement)rewardElement;
        if (accept(selectable.getElements()))
        {
          return true;
        }
      }
    }
    return false;
  }

  private boolean accept(ItemReward itemReward)
  {
    Proxy<Item> itemProxy=itemReward.getItemProxy();
    if (itemProxy!=null)
    {
      return (_itemId.intValue()==itemProxy.getId());
    }
    return false;
  }
}
