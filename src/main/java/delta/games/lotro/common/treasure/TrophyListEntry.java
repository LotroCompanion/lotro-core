package delta.games.lotro.common.treasure;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

/**
 * Entry in a 'trophy list':
 * <ul>
 * <li>probability (percentage: ]0-100]),
 * <li>item or treasure group,
 * <li>quantity.
 * </ul>
 * @author DAM
 */
public class TrophyListEntry
{
  private float _probability;
  private Proxy<Item> _item;
  private TreasureGroupProfile _treasureGroup;
  private int _quantity;

  /**
   * Constructor.
   * @param probability Probability.
   * @param item
   * @param treasureGroup
   * @param quantity
   */
  public TrophyListEntry(float probability, Proxy<Item> item, TreasureGroupProfile treasureGroup, int quantity)
  {
    _probability=probability;
    _item=item;
    _treasureGroup=treasureGroup;
    _quantity=quantity;
  }

  /**
   * Get the probability.
   * @return the probability.
   */
  public float getProbability()
  {
    return _probability;
  }

  /**
   * Get the rewarded item.
   * @return an item or <code>null</code>.
   */
  public Proxy<Item> getItem()
  {
    return _item;
  }

  /**
   * Get the treasure group.
   * @return the treasure group.
   */
  public TreasureGroupProfile getTreasureGroup()
  {
    return _treasureGroup;
  }

  /**
   * Get the quantity.
   * @return the quantity.
   */
  public int getQuantity()
  {
    return _quantity;
  }
}
