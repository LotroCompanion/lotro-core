package delta.games.lotro.lore.reputation;

import java.util.HashMap;

/**
 * Represents a level in a faction.
 * @author DAM
 */
public class FactionLevel
{
  private static final HashMap<String,FactionLevel> _mapByKey=new HashMap<String,FactionLevel>();

  /**
   * Enemy.
   */
  public static final FactionLevel ENEMY=new FactionLevel("ENEMY","Enemy",-1,0,10000);
  /**
   * Outsider.
   */
  public static final FactionLevel OUTSIDER=new FactionLevel("OUTSIDER","Outsider",-1,0,10000);
  /**
   * Neutral.
   */
  public static final FactionLevel NEUTRAL=new FactionLevel("NEUTRAL","Neutral",0,0,10000);
  /**
   * Acquaintance.
   */
  public static final FactionLevel ACQUAINTANCE=new FactionLevel("ACQUAINTANCE","Acquaintance",1,5,20000);
  /**
   * Friend.
   */
  public static final FactionLevel FRIEND=new FactionLevel("FRIEND","Friend",2,10,25000);
  /**
   * Ally.
   */
  public static final FactionLevel ALLY=new FactionLevel("ALLY","Ally",3,15,30000);
  /**
   * Kindred.
   */
  public static final FactionLevel KINDRED=new FactionLevel("KINDRED","Kindred",4,20,45000);
  /**
   * Respected.
   */
  public static final FactionLevel RESPECTED=new FactionLevel("RESPECTED","Respected",5,20,0); // TODO
  /**
   * Honoured.
   */
  public static final FactionLevel HONOURED=new FactionLevel("HONOURED","Honoured",6,20,0); // TODO
  /**
   * Celebrated.
   */
  public static final FactionLevel CELEBRATED=new FactionLevel("CELEBRATED","Celebrated",7,50,0);

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
    _mapByKey.put(name,this);
    _mapByKey.put(key,this);
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

  /**
   * Get a faction level using its identifying key.
   * @param key Key to use.
   * @return A faction level or <code>null</code> if not found.
   */
  public static FactionLevel getByKey(String key)
  {
    return _mapByKey.get(key);
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
