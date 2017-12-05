package delta.games.lotro.crafting;

/**
 * Represents a tier of a level in a crafting profession.
 * @author DAM
 */
public class CraftingLevelTier
{
  private CraftingLevel _level;
  private String _label;
  private int _xp;

  /**
   * Constructor.
   * @param level Level.
   * @param label Label.
   * @param xp XP amount.
   */
  public CraftingLevelTier(CraftingLevel level, String label, int xp)
  {
    _level=level;
    _label=label;
    _xp=xp;
  }

  /**
   * Get the parent crafting level.
   * @return the parent crafting level.
   */
  public CraftingLevel getLevel()
  {
    return _level;
  }

  /**
   * Get the title for this tier in this level.
   * @return A title.
   */
  public String getLabel()
  {
    return _label;
  }

  /**
   * Get the XP for this tier.
   * @return an XP value.
   */
  public int getXP()
  {
    return _xp;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
