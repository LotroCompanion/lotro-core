package delta.games.lotro.lore.items;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.treasure.TrophyList;

/**
 * Disenchantment results.
 * @author DAM
 */
public class DisenchantmentResult implements Identifiable
{
  private int _identifier;
  private CountedItem _countedItem;
  private TrophyList _trophyList;

  /**
   * Constructor.
   * @param identifier Item identifier.
   */
  public DisenchantmentResult(int identifier)
  {
    _identifier=identifier;
  }

  @Override
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the counted item.
   * @return the counted item.
   */
  public CountedItem getCountedItem()
  {
    return _countedItem;
  }

  /**
   * Set the counted item.
   * @param countedItem Counted item to set.
   */
  public void setCountedItem(CountedItem countedItem)
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
