package delta.games.lotro.common;

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
  public static final FactionLevel ENEMY=new FactionLevel("Enemy",-1);
  /**
   * Outsider.
   */
  public static final FactionLevel OUTSIDER=new FactionLevel("Outsider",-1);
  /**
   * Neutral.
   */
  public static final FactionLevel NEUTRAL=new FactionLevel("Neutral",0);
  /**
   * Acquaintance.
   */
  public static final FactionLevel ACQUAINTANCE=new FactionLevel("Acquaintance",1);
  /**
   * Friend.
   */
  public static final FactionLevel FRIEND=new FactionLevel("Friend",2);
  /**
   * Ally.
   */
  public static final FactionLevel ALLY=new FactionLevel("Ally",3);
  /**
   * Kindred.
   */
  public static final FactionLevel KINDRED=new FactionLevel("Kindred",4);

  private String _name;
  private int _value;

  private FactionLevel(String name, int value)
  {
    _name=name;
    _value=value;
    _mapByKey.put(name,this);
  }

  /**
   * Get the identifying key for this faction.
   * @return An identifying key.
   */
  public String getKey()
  {
    // TODO use a real key
    return _name;
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
