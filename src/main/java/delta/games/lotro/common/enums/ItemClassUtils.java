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

  private static final int ENHANCEMENT_RUNE_KEY=1;
  private static final int HERALDIC_TRACERY_KEY=2;
  private static final int WORD_OF_POWER_KEY=3;
  private static final int WORD_OF_MASTERY_KEY=4;
  private static final int WORD_OF_CRAFT_KEY=5;
  private static final int BOX_OF_ESSENCES_KEY=7;

  /**
   * Get the item class code for enhancement runes.
   * @return An item class code.
   */
  public static final int getEnhancementRuneCode()
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(ENHANCEMENT_RUNE_KEY*KEY_FACTOR);
  }

  /**
   * Get the item class code for heraldic traceries.
   * @return An item class code.
   */
  public static final int getHeraldicTraceryCode()
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(HERALDIC_TRACERY_KEY*KEY_FACTOR);
  }

  /**
   * Get the item class code for words of power.
   * @return An item class code.
   */
  public static final int getWordOfPowerCode()
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(WORD_OF_POWER_KEY*KEY_FACTOR);
  }

  /**
   * Get the item class code for words of mastery.
   * @return An item class code.
   */
  public static final int getWordOfMasteryCode()
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(WORD_OF_MASTERY_KEY*KEY_FACTOR);
  }

  /**
   * Get the item class code for words of craft.
   * @return An item class code.
  */
  public static final int getWordOfCraftCode()
  {
    return (ESSENCE_CODE*CATEGORY_FACTOR)+(WORD_OF_CRAFT_KEY*KEY_FACTOR);
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
