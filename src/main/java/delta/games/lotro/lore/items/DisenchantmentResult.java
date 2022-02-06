package delta.games.lotro.lore.items;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.treasure.TrophyList;

/**
 * Disenchantment results.
 * @author DAM
 */
public class DisenchantmentResult implements Identifiable,Named
{
  private Item _sourceItem;
  private CountedItem<Item> _countedItem;
  private TrophyList _trophyList;

  /**
   * Constructor.
   * @param sourceItem Source item.
   */
  public DisenchantmentResult(Item sourceItem)
  {
    _sourceItem=sourceItem;
  }

  @Override
  public int getIdentifier()
  {
    return _sourceItem.getIdentifier();
  }

  @Override
  public String getName()
  {
    return _sourceItem.getName();
  }

  /**
   * Get the counted item.
   * @return the counted item.
   */
  public CountedItem<Item> getCountedItem()
  {
    return _countedItem;
  }

  /**
   * Set the counted item.
   * @param countedItem Counted item to set.
   */
  public void setCountedItem(CountedItem<Item> countedItem)
  {
    _countedItem=countedItem;
  }

  /**
   * Get the trophy list.
   * @return a trophy list or <code>null</code>.
   */
  public TrophyList getTrophyList()
  {
    return _trophyList;
  }

  /**
   * Set the trophy list.
   * @param trophyList the trophy list to set.
   */
  public void setTrophyList(TrophyList trophyList)
  {
    _trophyList=trophyList;
  }
}
