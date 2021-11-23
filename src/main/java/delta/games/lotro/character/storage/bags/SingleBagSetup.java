package delta.games.lotro.character.storage.bags;

/**
 * Setup of a single bag.
 * <p>
 * Gives:
 * <ul>
 * <li>the bag index,
 * <li>the width of the bag,
 * <li>the total size (slots count) of the bag,
 * <li>the contents of each bag slot, determined by the index of the item in the bag data.
 * </ul>
 * @author DAM
 */
public class SingleBagSetup
{
  private int _bagIndex;
  private int _width;
  private int _size;
  private int[] _itemIndexes;

  /**
   * Constructor.
   * @param bagIndex Bag index.
   * @param size Bag size.
   */
  public SingleBagSetup(int bagIndex, int size)
  {
    _bagIndex=bagIndex;
    _size=size;
    _itemIndexes=new int[size];
  }

  /**
   * Get the bag index.
   * @return A bag index.
   */
  public int getBagIndex()
  {
    return _bagIndex;
  }

  /**
   * Get the bag width.
   * @return a width (slots count).
   */
  public int getWidth()
  {
    return _width;
  }

  /**
   * Set the width.
   * @param width the width to set.
   */
  public void setWidth(int width)
  {
    _width=width;
  }

  /**
   * Get the bag size.
   * @return a size (slots count).
   */
  public int getSize()
  {
    return _size;
  }

  /**
   * Get the index of the item at the specified slot index.
   * @param slotIndex Slot index.
   * @return An index or <code>null</code> if no item.
   */
  public Integer getItemIndexAt(int slotIndex)
  {
    if ((slotIndex<0) || (slotIndex>=_size))
    {
      return null;
    }
    int index=_itemIndexes[slotIndex];
    return index>0?Integer.valueOf(index):null;
  }

  /**
   * Set item index at the give slot index.
   * @param slotIndex Slot index (starting at 0).
   * @param itemIndex Item index to set.
   */
  public void setItemIndexAt(int slotIndex, Integer itemIndex)
  {
    if ((slotIndex>=0) && (slotIndex<_size))
    {
      int indexValue=(itemIndex!=null)?itemIndex.intValue():0;
      _itemIndexes[slotIndex]=indexValue;
    }
  }
}
