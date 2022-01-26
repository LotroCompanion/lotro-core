package delta.games.lotro.lore.items.details;

/**
 * Item XP.
 * @author dm
 */
public class ItemXP extends ItemDetail
{
  private int _amount;

  /**
   * Constructor.
   * @param amount Item XP amount.
   */
  public ItemXP(int amount)
  {
    _amount=amount;
  }

  /**
   * Get the item XP amount.
   * @return a quantity.
   */
  public int getAmount()
  {
    return _amount;
  }
}
