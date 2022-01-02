package delta.games.lotro.lore.items.details;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for a collection of item details.
 * @author DAM
 */
public class ItemDetailsManager
{
  private List<ItemDetail> _details;

  /**
   * Constructor.
   */
  public ItemDetailsManager()
  {
    _details=new ArrayList<ItemDetail>();
  }

  /**
   * Add an item detail.
   * @param itemDetail Detail to add.
   */
  public void addItemDetail(ItemDetail itemDetail)
  {
    _details.add(itemDetail);
  }

  /**
   * Get the managed item details
   * @return a list of item details.
   */
  public List<ItemDetail> getItemDetails()
  {
    return _details;
  }
}
