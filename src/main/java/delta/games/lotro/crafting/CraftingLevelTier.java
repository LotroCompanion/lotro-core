package delta.games.lotro.crafting;

/**
 * Represents a tier of a level in a crafting profession.
 * @author DAM
 */
public class CraftingLevelTier
{
  private String _label;
  private int _XP;

  /**
   * Constructor.
   * @param label Label.
   * @param XP XP amount.
   */
  public CraftingLevelTier(String label, int XP)
  {
    _label=label;
    _XP=XP;
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
    return _XP;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
