package delta.games.lotro.lore.reputation;

/**
 * Represents a level in a faction.
 * @author DAM
 */
public class FactionLevel
{
  private int _tier;
  private String _name;
  private int _lotroPoints;
  private int _requiredXp;
  private String _deedKey;

  /**
   * Constructor.
   * @param tier Tier.
   * @param name Level name.
   * @param lotroPoints LOTRO points given when reaching this level.
   * @param requiredXp XP points required to reach the next level.
   */
  public FactionLevel(int tier, String name, int lotroPoints, int requiredXp)
  {
    _tier=tier;
    _name=name;
    _lotroPoints=lotroPoints;
    _requiredXp=requiredXp;
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
