package delta.games.lotro.lore.items.legendary2;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.lore.items.Item;

/**
 * Enhancement rune.
 * @author DAM
 */
public class EnhancementRune implements Identifiable,Named
{
  private Item _item;
  private Integer _tier;
  private int _minItemLevel;
  private int _maxItemLevel;
  private int _increment;

  /**
   * Constructor.
   * @param item Associated item.
   * @param minItemLevel Minimum item level.
   * @param maxItemLevel Maximum item level.
   * @param increment Level up increment.
   */
  public EnhancementRune(Item item, int minItemLevel, int maxItemLevel, int increment)
  {
    _item=item;
    initTier();
    _minItemLevel=minItemLevel;
    _maxItemLevel=maxItemLevel;
    _increment=increment;
  }

  @Override
  public int getIdentifier()
  {
    return (_item!=null)?_item.getIdentifier():0;
  }

  /**
   * Get the name of this tracery.
   * @return a name.
   */
  public String getName()
  {
    return (_item!=null)?_item.getName():"";
  }

  /**
   * Get the associated item.
   * @return the associated item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the tier of this rune.
   * @return A tier or <code>null</code> if none.
   */
  public Integer getTier()
  {
    return _tier;
  }

  private void initTier()
  {
    if (_item==null)
    {
      return;
    }
    _tier=_item.getTier();
  }

  /**
   * Get the minimum item level.
   * @return an item level.
   */
  public int getMinItemLevel()
  {
    return _minItemLevel;
  }

  /**
   * Get the maximum item level.
   * @return an item level.
   */
  public int getMaxItemLevel()
  {
    return _maxItemLevel;
  }

  /**
   * Get the level-up increment.
   * @return level-up increment.
   */
  public int getLevelUpIncrement()
  {
    return _increment;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Enhancement rune ID=").append(getIdentifier());
    sb.append(", name=").append(getName());
    sb.append(", min Item Level=").append(_minItemLevel);
    sb.append(", max Item Level=").append(_maxItemLevel);
    sb.append(", increment=").append(_increment);
    if (_item!=null)
    {
      sb.append(", min Char Level=").append(_item.getMinLevel());
      sb.append(", max Char Level=").append(_item.getMaxLevel());
    }
    return sb.toString();
  }
}
