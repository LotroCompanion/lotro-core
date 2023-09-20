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

  /**
   * Get the details of a given type.
   * @param clazz Type class.
   * @return A list of details.
   */
  @SuppressWarnings("unchecked")
  public <T extends ItemDetail> List<T> getItemDetails(Class<T> clazz)
  {
    List<T> ret=new ArrayList<T>();
    for(ItemDetail detail : _details)
    {
      if (clazz.isAssignableFrom(detail.getClass()))
      {
        ret.add((T)detail);
      }
    }
    return ret;
  }

  /**
   * Get the first detail of a given type.
   * @param clazz Type class.
   * @return A detail or <code>null</code> if not found.
   */
  @SuppressWarnings("unchecked")
  public <T extends ItemDetail> T getFirstItemDetail(Class<T> clazz)
  {
    for(ItemDetail detail : _details)
    {
      if (clazz.isAssignableFrom(detail.getClass()))
      {
        return (T)detail;
      }
    }
    return null;
  }
}
