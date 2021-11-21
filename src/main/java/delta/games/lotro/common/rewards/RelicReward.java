package delta.games.lotro.common.rewards;

import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.utils.Proxy;

/**
 * Relic reward.
 * @author DAM
 */
public class RelicReward extends RewardElement
{
  /**
   * Relic proxy.
   */
  private Proxy<Relic> _relic;
  /**
   * Item quantity.
   */
  private int _quantity;

  /**
   * Constructor.
   * @param relic Relic proxy.
   * @param quantity Quantity.
   */
  public RelicReward(Proxy<Relic> relic, int quantity)
  {
    _relic=relic;
    _quantity=quantity;
  }

  /**
   * Get the relic proxy.
   * @return a relic proxy or <code>null</code> if not set.
   */
  public Proxy<Relic> getRelicProxy()
  {
    return _relic;
  }

  /**
   * Get quantity.
   * @return a quantity.
   */
  public int getQuantity()
  {
    return _quantity;
  }

  /**
   * Add a quantity.
   * @param quantity Quantity to add.
   */
  public void addQuantity(int quantity)
  {
    _quantity+=quantity;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_relic!=null)
    {
      if (_quantity!=1)
      {
        sb.append(_quantity).append("x");
      }
      sb.append(_relic.getName());
    }
    else
    {
      sb.append('?');
    }
    return sb.toString();
  }
}
