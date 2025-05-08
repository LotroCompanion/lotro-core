package delta.games.lotro.lore.items.details;

/**
 * Virtue XP.
 * @author DAM
 */
public class VirtueXP extends ItemDetail
{
  private int _amount;
  private boolean _bonus;

  /**
   * Constructor.
   * @param amount Virtue XP amount.
   * @param bonus Bonus or raw XP.
   */
  public VirtueXP(int amount, boolean bonus)
  {
    _amount=amount;
    _bonus=bonus;
  }

  /**
   * Get the virtue XP amount.
   * @return a quantity.
   */
  public int getAmount()
  {
    return _amount;
  }

  /**
   * Indicates if this is 'bonus' XP or raw XP.
   * @return <code>true</code> for bonus XP, <code>false</code> for raw XP.
   */
  public boolean isBonus()
  {
    return _bonus;
  }
}
