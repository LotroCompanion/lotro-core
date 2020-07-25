package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.LockType;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Filter for quests with a given lock type.
 * @author DAM
 */
public class LockTypeFilter implements Filter<QuestDescription>
{
  private LockType _lockType;

  /**
   * Constructor.
   * @param lockType Lock type to select (may be <code>null</code>).
   */
  public LockTypeFilter(LockType lockType)
  {
    _lockType=lockType;
  }

  /**
   * Get the lock type to use.
   * @return A lock type or <code>null</code>.
   */
  public LockType getLockType()
  {
    return _lockType;
  }

  /**
   * Set the lock type to select.
   * @param lockType Lock type to use, may be <code>null</code>.
   */
  public void setLockType(LockType lockType)
  {
    _lockType=lockType;
  }

  @Override
  public boolean accept(QuestDescription quest)
  {
    if (_lockType==null)
    {
      return true;
    }
    LockType lockType=quest.getLockType();
    return (_lockType==lockType);
  }
}
