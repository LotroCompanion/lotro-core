package delta.games.lotro.lore.parameters;

/**
 * Global game parameters.
 * @author DAM
 */
public class GameParameters
{
  private int _maxCharacterLevel;
  private int _maxLegendaryItemLevel;
  private int _maxVirtueRank;

  /**
   * Constructor.
   */
  public GameParameters()
  {
    _maxCharacterLevel=150;
    _maxLegendaryItemLevel=520;
    _maxVirtueRank=92;
  }

  /**
   * Get the maximum character leveL.
   * @return the maximum character leveL.
   */
  public int getMaxCharacterLevel()
  {
    return _maxCharacterLevel;
  }

  /**
   * Set the maximum character level.
   * @param maxCharacterLevel the level to set
   */
  public void setMaxCharacterLevel(int maxCharacterLevel)
  {
    _maxCharacterLevel=maxCharacterLevel;
  }

  /**
   * Get the maximum item level for legendary items.
   * @return an item level.
   */
  public int getMaxLegendaryItemLevel()
  {
    return _maxLegendaryItemLevel;
  }

  /**
   * Set the maximum item level for legendary items.
   * @param maxLegendaryItemLevel the level to set.
   */
  public void setMaxLegendaryItemLevel(int maxLegendaryItemLevel)
  {
    _maxLegendaryItemLevel=maxLegendaryItemLevel;
  }

  /**
   * Get the maximum virtue rank.
   * @return the maximum virtue rank.
   */
  public int getMaxVirtueRank()
  {
    return _maxVirtueRank;
  }

  /**
   * Set the maximum virtue rank.
   * @param maxVirtueRank the rank to set.
   */
  public void setMaxVirtueRank(int maxVirtueRank)
  {
    _maxVirtueRank=maxVirtueRank;
  }
}
