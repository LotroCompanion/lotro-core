package delta.games.lotro.lore.items.scaling;

import delta.common.utils.NumericTools;

/**
 * Item Level Bonus.
 * @author DAM
 */
public class ItemLevelBonus
{
  private int _bonusLimit;
  private float _bonusChance;

  /**
   * Constructor.
   * @param bonusLimit Bonus limit.
   * @param bonusChance Bonus chance.
   */
  public ItemLevelBonus(int bonusLimit, float bonusChance)
  {
    _bonusLimit=bonusLimit;
    _bonusChance=bonusChance;
  }

  /**
   * Get the bonus limit.
   * @return the bonus limit.
   */
  public int getBonusLimit()
  {
    return _bonusLimit;
  }

  /**
   * Get the bonus chance.
   * @return the bonus chance.
   */
  public float getBonusChance()
  {
    return _bonusChance;
  }

  @Override
  public String toString()
  {
    return "+"+_bonusLimit+" ("+_bonusChance+")";
  }

  /**
   * Build an item level bonus from a string specification.
   * @param bonusStr Input string.
   * @return A new item level bonus.
   */
  public static ItemLevelBonus fromString(String bonusStr)
  {
    int index=bonusStr.indexOf(':');
    String limitStr=bonusStr.substring(0,index);
    int limit=NumericTools.parseInt(limitStr,0);
    String chanceStr=bonusStr.substring(index+1);
    float chance=NumericTools.parseFloat(chanceStr,0.0f);
    return new ItemLevelBonus(limit,chance);
  }

  /**
   * Get a string specification from this object.
   * @return A string.
   */
  public String asString()
  {
    return _bonusLimit+":"+_bonusChance;
  }
}
