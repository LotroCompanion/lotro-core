package delta.games.lotro.lore.reputation;

/**
 * Represents a level in a faction.
 * @author DAM
 */
public class FactionLevel
{
  private String _key;
  private String _name;
  private int _value;
  private int _lotroPoints;
  private int _requiredXp;
  private String _deedKey;

  /**
   * Constructor.
   * @param key Identifying key.
   * @param name Level name.
   * @param value Level rank.
   * @param lotroPoints LOTRO points given when reaching this level.
   * @param requiredXp XP points required to reach the next level.
   */
  public FactionLevel(String key, String name, int value, int lotroPoints, int requiredXp)
  {
    _key=key;
    _name=name;
    _value=value;
    _lotroPoints=lotroPoints;
    _requiredXp=requiredXp;
  }

  /**
   * Get the identifying key for this faction.
   * @return An identifying key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the name of this level.
   * @return A name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the value of this level.
   * @return the value of this level.
   */
  public int getValue()
  {
    return _value;
  }

  /**
   * Get the LOTRO points given when reaching this level.
   * @return a LOTRO points count.
   */
  public int getLotroPoints()
  {
    return _lotroPoints;
  }

  /**
   * Get the XP points required to reach the next level.
   * @return an XP points count.
   */
  public int getRequiredXp()
  {
    return _requiredXp;
  }

  /**
   * Get the key of the associated deed.
   * @return A deed key or <code>null</code> if none.
   */
  public String getDeedKey()
  {
    return _deedKey;
  }

  /**
   * Set the key of the associated deed.
   * @param deedKey A deed key or <code>null</code>.
   */
  public void setDeedKey(String deedKey)
  {
    _deedKey=deedKey;
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
