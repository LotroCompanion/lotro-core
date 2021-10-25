package delta.games.lotro.lore.items.legendary2;

import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Definition of a socket entry.
 * @author DAM
 */
public class SocketEntry
{
  private SocketType _type;
  private int _unlockItemLevel;

  /**
   * Constructor.
   * @param type Socket type.
   * @param unlockItemLevel Unlock item level.
   */
  public SocketEntry(SocketType type, int unlockItemLevel)
  {
    _type=type;
    _unlockItemLevel=unlockItemLevel;
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
   * Get the unlock item level.
   * @return an item level.
   */
  public int getUnlockItemLevel()
  {
    return _unlockItemLevel;
  }

  /**
   * Indicates if this socket is enabled or not for the given LI instance.
   * @param itemInstance Item instance to use.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEnabled(ItemInstance<? extends Item> itemInstance)
  {
    Integer itemLevel=itemInstance.getEffectiveItemLevel();
    int itemLevelInt=(itemLevel!=null)?itemLevel.intValue():1;
    return isEnabled(itemLevelInt);
  }

  /**
   * Indicates if this socket is enabled or not for a LI with the given item level.
   * @param itemLevel Item level to use.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEnabled(int itemLevel)
  {
    return itemLevel>=_unlockItemLevel;
  }

  @Override
  public String toString()
  {
    return _type+" (unlocked at item level "+_unlockItemLevel+")";
  }
}
