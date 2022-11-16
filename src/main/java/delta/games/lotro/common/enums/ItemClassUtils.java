package delta.games.lotro.common.enums;

/**
 * Utility methods for item classes.
 * @author DAM
 */
public class ItemClassUtils
{
  /**
   * Code for essences.
   */
  public static final int ESSENCE_CODE=235;
  private static final int CATEGORY_FACTOR=10000;
  private static final int KEY_FACTOR=100;

  private static final int ESSENCE_KEY=0;
  private static final int ENHANCEMENT_RUNE_KEY=1;
  private static final int HERALDIC_TRACERY_KEY=2;
  private static final int WORD_OF_POWER_KEY=3;
  private static final int WORD_OF_MASTERY_KEY=4;
  private static final int WORD_OF_CRAFT_KEY=5;
  private static final int ESSENCE_OF_WAR_KEY=6;
  private static final int BOX_OF_ESSENCES_KEY=7;
  private static final int CLOAK_ESSENCE_KEY=8;
  private static final int NECKLACE_ESSENCE_KEY=9;

  /**
   * Get the item class code for essences.
   * @param tier Tier.
   * @return An item class code.
   */
  public static final int getEssenceCode(int tier)
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(ESSENCE_KEY*KEY_FACTOR)+tier;
  }

  /**
   * Get the item class code for enhancement runes.
   * @param tier Tier.
   * @return An item class code.
   */
  public static final int getEnhancementRuneCode(int tier)
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(ENHANCEMENT_RUNE_KEY*KEY_FACTOR)+tier;
  }

  /**
   * Get the item class code for heraldic traceries.
   * @param tier Tier.
   * @return An item class code.
   */
  public static final int getHeraldicTraceryCode(int tier)
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(HERALDIC_TRACERY_KEY*KEY_FACTOR)+tier;
  }

  /**
   * Get the item class code for words of power.
   * @param tier Tier.
   * @return An item class code.
   */
  public static final int getWordOfPowerCode(int tier)
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(WORD_OF_POWER_KEY*KEY_FACTOR)+tier;
  }

  /**
   * Get the item class code for words of mastery.
   * @param tier Tier.
   * @return An item class code.
   */
  public static final int getWordOfMasteryCode(int tier)
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(WORD_OF_MASTERY_KEY*KEY_FACTOR)+tier;
  }

  /**
   * Get the item class code for words of craft.
   * @param tier Tier.
   * @return An item class code.
   */
  public static final int getWordOfCraftCode(int tier)
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(WORD_OF_CRAFT_KEY*KEY_FACTOR)+tier;
  }

  /**
   * Get the item class code for essences of war.
   * @param tier Tier.
   * @return An item class code.
   */
  public static final int getEssenceOfWarCode(int tier)
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(ESSENCE_OF_WAR_KEY*KEY_FACTOR)+tier;
  }

  /**
   * Get the item class code for cloak essences.
   * @param tier Tier.
   * @return An item class code.
   */
  public static final int getCloakEssenceCode(int tier)
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(CLOAK_ESSENCE_KEY*KEY_FACTOR)+tier;
  }

  /**
   * Get the item class code for necklace essences.
   * @param tier Tier.
   * @return An item class code.
   */
  public static final int getNecklaceEssenceCode(int tier)
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(NECKLACE_ESSENCE_KEY*KEY_FACTOR)+tier;
  }

  /**
   * Get the item class code for boxes of essences.
   * @return An item class code.
   */
  public static final int getBoxOfEssenceCode()
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(BOX_OF_ESSENCES_KEY*KEY_FACTOR);
  }
}
