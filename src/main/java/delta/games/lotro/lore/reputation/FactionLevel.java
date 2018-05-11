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

  @Override
  public String toString()
  {
    return _name;
  }
}

/*

Enemy   10,000      * Only currently possible with The Ale Association and The Inn League
Outsider  10,000      * Starting level for Lossoth of Forochel
Neutral / Guild Initiate  0     0     * Starting level for most factions
Acquaintance / Apprentice of the Guild  10,000    10,000    Access to reputation areas & their vendors / Access to Guild Expert Recipes
Friend / Journeyman of the Guild  20,000    30,000    10% Travel discount from faction stables / Access to Guild Artisan Recipes
Ally / Expert of the Guild  25,000    55,000    Discounted repair costs (approx 20-25%) and discount (5%) from reputation vendors / Access to Guild Master Recipes
Kindred / Artisan of the Guild  30,000    85,000    Reputation mounts; bonus repair discount (22.5%) / Access to Guild Supreme Recipes
– / Master of the Guild   45,000    130,000     – / Access to Guild Westfold Recipes 
*/
