package delta.games.lotro.lore.trade.vendor;

import java.util.ArrayList;
import java.util.List;

/**
 * Vendor aspect of an NPC.
 * @author DAM
 */
public class VendorNpc
{
  private List<SellList> _sellLists;
  private boolean _buys;
  private List<Integer> _discounts;
  private boolean _sellsWearableItems;
  private float _sellFactor;

  /**
   * Constructor.
   */
  public VendorNpc()
  {
    _sellLists=new ArrayList<SellList>();
    _buys=false;
    _discounts=new ArrayList<Integer>();
    _sellsWearableItems=false;
    _sellFactor=1;
  }

  /**
   * Add a sell list.
   * @param sellList List to add.
   */
  public void addSellList(SellList sellList)
  {
    _sellLists.add(sellList);
  }

  /**
   * Get the sell lists.
   * @return a list of sell lists.
   */
  public List<SellList> getSellLists()
  {
    return _sellLists;
  }

  /**
   * Indicates if this vendor also buys items.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean buys()
  {
    return _buys;
  }

  /**
   * Add a discount.
   * @param discountId Discount to add.
   */
  public void addDiscount(int discountId)
  {
    _discounts.add(Integer.valueOf(discountId));
  }

  /**
   * Get the discounts.
   * @return a list discounts.
   */
  public List<Integer> getDiscounts()
  {
    return _discounts;
  }

  /**
   * Indicates if this vendor sells wearable items.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean sellsWearableItems()
  {
    return _sellsWearableItems;
  }

  /**
   * Get the sell factor.
   * @return a factor.
   */
  public float getSellFactor()
  {
    return _sellFactor;
  }
}
