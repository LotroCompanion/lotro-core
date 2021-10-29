package delta.games.lotro.character.stats;

import delta.games.lotro.character.BasicCharacterAttributes;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.sets.ItemsSet;

/**
 * Utility methods related to stats computation.
 * @author DAM
 */
public class StatsComputationUtils
{
  /**
   * Check stats shall be used for the given item.
   * @param item Item to use.
   * @return <code>true</code> to use stats, <code>false</code> otherwise.
   */
  public static boolean itemIsApplicable(ItemInstance<? extends Item> item)
  {
    // Check for broken items
    Integer durability=item.getEffectiveDurability();
    if ((durability!=null) && (durability.intValue()==0))
    {
      // Broken
      return false;
    }
    // Check for level requirements
    BasicCharacterAttributes wearer=item.getWearer();
    int characterLevel=(wearer!=null)?wearer.getLevel():1;
    Integer minLevel=item.getEffectiveMinLevel();
    if ((minLevel!=null) && (characterLevel<minLevel.intValue()))
    {
      // Character is too low level
      return false;
    }
    Integer maxLevel=item.getReference().getMaxLevel();
    if ((maxLevel!=null) && (characterLevel>maxLevel.intValue()))
    {
      // Character is too high level
      return false;
    }
    return true;
  }

  /**
   * Check set can be used for the given character level.
   * @param set Set to use.
   * @param characterLevel Character level.
   * @return <code>true</code> to use the set, <code>false</code> otherwise.
   */
  public static boolean setIsApplicable(ItemsSet set, int characterLevel)
  {
    int minLevel=set.getRequiredMinLevel();
    if (characterLevel<minLevel)
    {
      // Character is too low level
      return false;
    }
    Integer maxLevel=set.getRequiredMaxLevel();
    if ((maxLevel!=null) && (characterLevel>maxLevel.intValue()))
    {
      // Character is too high level
      return false;
    }
    return true;
  }
}
