package delta.games.lotro.lore.items.essences;

import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemCategory;

/**
 * Essence.
 * @author DAM
 */
public class Essence extends Item
{
  private SocketType _type;

  @Override
  public ItemCategory getCategory()
  {
    return ItemCategory.ESSENCE;
  }

  /**
   * Get the socket type.
   * @return a socket type.
   */
  public SocketType getType()
  {
    return _type;
  }

  /**
   * Set socket type.
   * @param type Type to set.
   */
  public void setType(SocketType type)
  {
    _type=type;
  }
}
