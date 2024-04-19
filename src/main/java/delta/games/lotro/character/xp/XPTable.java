package delta.games.lotro.character.xp;

/**
 * XP table.
 * @author DAM
 */
public class XPTable
{
  private long[] _xpTable;

  /**
   * Constructor.
   */
  public XPTable()
  {
    // Nothing!
  }

  /**
   * Get the XP table.
   * @return the XP table.
   */
  public long[] getXpTable()
  {
    return _xpTable;
  }

  /**
   * Set the XP table.
   * @param xpTable XP table to set.
   */
  public void setXpTable(long[] xpTable)
  {
    _xpTable=xpTable;
  }

  /**
   * Get the level for a XP value.
   * @param xp XP value.
   * @return A level;
   */
  public int getLevel(long xp)
  {
    int ret=1;
    for(int i=1;i<_xpTable.length;i++)
    {
      if (_xpTable[i]<=xp)
      {
        ret=i;
      }
      else
      {
        break;
      }
    }
    return ret;
  }

  /**
   * Get the XP for a legendary level.
   * @param level Level to use.
   * @return an XP value.
   */
  public long getXp(int level)
  {
    if ((level>=0) && (level<_xpTable.length))
    {
      return _xpTable[level];
    }
    return 0;
  }
}
