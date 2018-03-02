package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Rewards;
import delta.games.lotro.common.objects.ObjectItem;
import delta.games.lotro.common.objects.ObjectsSet;

/**
 * Filter for rewards that contain an item.
 * @author DAM
 */
public class ItemRewardFilter implements Filter<Rewards>
{
  private String _itemName;

  /**
   * Constructor.
   * @param itemName Name of item to select (may be <code>null</code>).
   */
  public ItemRewardFilter(String itemName)
  {
    _itemName=itemName;
  }

  /**
   * Get the item name to use.
   * @return An item name or <code>null</code>.
   */
  public String getItemName()
  {
    return _itemName;
  }

  /**
   * Set the item name to select.
   * @param itemName Item name to use, may be <code>null</code>.
   */
  public void setItemName(String itemName)
  {
    _itemName=itemName;
  }

  public boolean accept(Rewards rewards)
  {
    if (_itemName==null)
    {
      return true;
    }
    ObjectsSet objects=rewards.getObjects();
    int nbObjects=objects.getNbObjectItems();
    for(int i=0;i<nbObjects;i++)
    {
      ObjectItem objectItem=objects.getItem(i);
      if (_itemName.equals(objectItem.getName()))
      {
        return true;
      }
    }
    return false;
  }
}
