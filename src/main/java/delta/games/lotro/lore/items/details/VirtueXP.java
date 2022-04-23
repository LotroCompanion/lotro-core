package delta.games.lotro.lore.items.details;

/**
 * Virtue XP.
 * @author DAM
 */
public class VirtueXP extends ItemDetail
{
  private int _amount;

  /**
   * Constructor.
   * @param amount Virtue XP amount.
   */
  public VirtueXP(int amount)
  {
    _amount=amount;
  }

  /**
   * Get the virtue XP amount.
   * @return a quantity.
   */
  public int getAmount()
  {
    return _amount;
  }
}
