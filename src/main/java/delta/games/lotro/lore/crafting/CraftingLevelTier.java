package delta.games.lotro.lore.crafting;

import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Represents a tier of a level in a crafting profession.
 * @author DAM
 */
public class CraftingLevelTier
{
  private TitleDescription _title;
  private int _xp;

  /**
   * Constructor.
   */
  public CraftingLevelTier()
  {
    _title=null;
    _xp=0;
  }

  /**
   * Get the title for this tier in this level.
   * @return A title.
   */
  public String getLabel()
  {
    return (_title!=null)?_title.getName():"";
  }

  /**
   * Get the associated title.
   * @return a title or <code>null</code> if not set.
   */
  public TitleDescription getTitle()
  {
    return _title;
  }

  /**
   * Set the associated title.
   * @param title Title to set.
   */
  public void setTitle(TitleDescription title)
  {
    _title=title;
  }

  /**
   * Get the XP for this tier.
   * @return an XP value.
   */
  public int getXP()
  {
    return _xp;
  }

  /**
   * Set the XP for this tier.
   * @param xp an XP value.
   */
  public void setXP(int xp)
  {
    _xp=xp;
  }

  @Override
  public String toString()
  {
    return getLabel();
  }
}
