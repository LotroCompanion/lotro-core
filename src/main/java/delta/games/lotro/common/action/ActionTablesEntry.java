package delta.games.lotro.common.action;

/**
 * Entry of an action tables.
 * @author DAM
 */
public class ActionTablesEntry
{
  private ActionTable _table;
  private Integer _minLevel;
  private Integer _maxLevel;

  /**
   * Constructor.
   * @param table Managed table.
   */
  public ActionTablesEntry(ActionTable table)
  {
    _table=table;
  }

  /**
   * Get the managed table.
   * @return an action table.
   */
  public ActionTable getTable()
  {
    return _table;
  }

  /**
   * Get the minimum level requirement.
   * @return A level or <code>null</code>.
   */
  public Integer getMinLevel()
  {
    return _minLevel;
  }

  /**
   * Set the minimum level requirement.
   * @param minLevel Level to set (may be <code>null</code>).
   */
  public void setMinLevel(Integer minLevel)
  {
    _minLevel=minLevel;
  }

  /**
   * Get the maximum level requirement.
   * @return A level or <code>null</code>.
   */
  public Integer getMaxLevel()
  {
    return _maxLevel;
  }

  /**
   * Set the maximum level requirement.
   * @param maxLevel Level to set (may be <code>null</code>).
   */
  public void setMaxLevel(Integer maxLevel)
  {
    _maxLevel=maxLevel;
  }
}
