package delta.games.lotro.lore.items.details;

import delta.games.lotro.lore.reputation.Faction;

/**
 * Reputation amount provided by an Item.
 * @author DAM
 */
public class ItemReputation extends ItemDetail
{
  private Faction _faction;
  private int _amount;

  /**
   * Constructor.
   * @param faction Faction.
   * @param amount Reputation amount.
   */
  public ItemReputation(Faction faction, int amount)
  {
    _faction=faction;
    _amount=amount;
  }

  /**
   * Get the targeted faction.
   * @return A faction.
   */
  public Faction getFaction()
  {
    return _faction;
  }

  /**
   * Get the reputation amount.
   * @return a quantity.
   */
  public int getAmount()
  {
    return _amount;
  }
}
