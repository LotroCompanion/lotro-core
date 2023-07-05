package delta.games.lotro.common.treasure;

import delta.common.utils.l10n.L10n;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.items.Item;

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
  private Item _item;
  private TreasureGroupProfile _treasureGroup;
  private int _quantity;

  /**
   * Constructor.
   * @param probability Probability.
   * @param item Item.
   * @param quantity Quantity.
   */
  public TrophyListEntry(float probability, Item item, int quantity)
  {
    _probability=probability;
    _item=item;
    _quantity=quantity;
  }

  /**
   * Constructor.
   * @param probability Probability.
   * @param treasureGroup Treasure group profile.
   */
  public TrophyListEntry(float probability, TreasureGroupProfile treasureGroup)
  {
    _probability=probability;
    _treasureGroup=treasureGroup;
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
  public Item getItem()
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

  /**
   * Get the probability label.
   * @return a readable label for probability.
   */
  public String getProbabilityLabel() {
    if (_probability==1.0)
    {
      return "Always";
    }
    return L10n.getString(_probability*100,1)+"%";
  }

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append(getProbabilityLabel());
    if (_quantity!=1)
    {
      sb.append(' ').append(_quantity).append('x');
    }
    if (_item!=null)
    {
      sb.append(' ').append(_item.getName());
      sb.append(EndOfLine.NATIVE_EOL);
    }
    else if (_treasureGroup!=null)
    {
      sb.append(EndOfLine.NATIVE_EOL);
      _treasureGroup.dump(sb,level+1);
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    dump(sb,0);
    return sb.toString().trim();
  }
}
