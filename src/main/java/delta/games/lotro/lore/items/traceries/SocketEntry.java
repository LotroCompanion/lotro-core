package delta.games.lotro.lore.items.traceries;

import delta.games.lotro.common.enums.SocketType;

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

  @Override
  public String toString()
  {
    return _type+" (unlocked at item level "+_unlockItemLevel;
  }
}
