package delta.games.lotro.lore.hobbies.rewards;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.lore.items.Item;

/**
 * Entry in a hobby drop table.
 * @author DAM
 */
public class HobbyDropTableEntry implements Identifiable,Named
{
  private Item _item;
  private float _dropPercentage;

  /**
   * Constructor.
   * @param item Managed item.
   * @param dropPercentage Drop percentage.
   */
  public HobbyDropTableEntry(Item item, float dropPercentage)
  {
    _item=item;
    _dropPercentage=dropPercentage;
  }

  @Override
  public int getIdentifier()
  {
    return _item.getIdentifier();
  }

  @Override
  public String getName()
  {
    return _item.getName();
  }

  /**
   * Get the managed item.
   * @return the managed item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the drop percentage.
   * @return the drop percentage.
   */
  public float getDropPercentage()
  {
    return _dropPercentage;
  }

  /**
   * Get the drop percentage as a displayable string.
   * @return A displayable percentage value.
   */
  public String getDropPercentageAsString()
  {
    return L10n.getString(_dropPercentage,2)+"%";
  }

  @Override
  public String toString()
  {
    return _item+" => "+getDropPercentageAsString();
  }
}
