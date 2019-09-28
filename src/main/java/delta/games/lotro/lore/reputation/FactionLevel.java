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
  private int _tier;
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
   * Get the identifying key for this level.
   * @return An identifying key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the identifying key for this level.
   * @param key Key to set.
   */
  public void setKey(String key)
  {
    _key=key;
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
   * Set the value of this level.
   * @param value Value to set.
   */
  public void setValue(int value)
  {
    _value=value;
  }

  /**
   * Get the tier of this level.
   * @return the tier of this level.
   */
  public int getTier()
  {
    return _tier;
  }

  /**
   * Set the tier of this level.
   * @param tier Tier to set.
   */
  public void setTier(int tier)
  {
    _tier=tier;
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
   * Set the LOTRO points given when reaching this level.
   * @param lotroPoints LOTRO points count.
   */
  public void setLotroPoints(int lotroPoints)
  {
    _lotroPoints=lotroPoints;
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
   * Set the XP points required to reach the next level.
   * @param requiredXp an XP points count.
   */
  public void setRequiredXp(int requiredXp)
  {
    _requiredXp=requiredXp;
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
