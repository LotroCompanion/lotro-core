package delta.games.lotro.lore.relics.melding;

import delta.games.lotro.common.Named;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * A relic melding output entry. It contains:
 * <ul>
 * <li>a weight,
 * <li>a relic OR
 * <li>an item.
 * </ul>
 * @author DAM
 */
public class RelicMeldingOutputEntry
{
  private int _weight;
  private Relic _relic;
  private Item _item;

  /**
   * Constructor.
   * @param weight Weight.
   * @param relic Referenced relic.
   */
  public RelicMeldingOutputEntry(int weight, Relic relic)
  {
    _weight=weight;
    _relic=relic;
    _item=null;
  }

  /**
   * Constructor.
   * @param weight Weight.
   * @param item Referenced item.
   */
  public RelicMeldingOutputEntry(int weight, Item item)
  {
    _weight=weight;
    _relic=null;
    _item=item;
  }

  /**
   * Get the weight.
   * @return the weight.
   */
  public int getWeight()
  {
    return _weight;
  }

  /**
   * Get the referenced relic.
   * @return a relic.
   */
  public Relic getRelic()
  {
    return _relic;
  }

  /**
   * Get the referenced item.
   * @return an item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the managed result (relic or item).
   * @return A result.
   */
  public Named getResult()
  {
    return (_relic!=null)?_relic:_item;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append('(').append(_weight).append(") ");
    if (_relic!=null)
    {
      sb.append(_relic.getName());
    }
    else if (_item!=null)
    {
      sb.append(_item.getName());
    }
    else
    {
      sb.append('?');
    }
    return sb.toString();
  }
}
