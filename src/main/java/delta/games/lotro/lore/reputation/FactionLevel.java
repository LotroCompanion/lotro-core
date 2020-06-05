package delta.games.lotro.lore.reputation;

/**
 * Represents a level in a faction.
 * @author DAM
 */
public class FactionLevel
{
  private int _tier;
  private String _key;
  private String _name;
  private int _lotroPoints;
  private int _requiredReputation;
  private String _deedKey;

  /**
   * Constructor.
   * @param tier Tier.
   * @param name Level name.
   * @param lotroPoints LOTRO points given when reaching this level.
   * @param requiredXp XP points required to reach this level.
   */
  public FactionLevel(int tier, String name, int lotroPoints, int requiredXp)
  {
    _tier=tier;
    _key=null;
    _name=name;
    _lotroPoints=lotroPoints;
    _requiredReputation=requiredXp;
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
   * Get the legacy key, if any.
   * @return A string key or <code>null</code>.
   */
  public String getLegacyKey()
  {
    return _key;
  }

  /**
   * Set the legacy key.
   * @param key Legacy key to set.
   */
  public void setLegacyKey(String key)
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
   * Get the reputation points required to reach this level.
   * @return a reputation points count.
   */
  public int getRequiredReputation()
  {
    return _requiredReputation;
  }

  /**
   * Set the reputation points required to reach this level.
   * @param requiredReputation a reputation points count.
   */
  public void setRequiredReputation(int requiredReputation)
  {
    _requiredReputation=requiredReputation;
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
