package delta.games.lotro.lore.items.legendary.non_imbued;

/**
 * Legacy instance on a non-imbued legendary item.
 * @author DAM
 */
public abstract class NonImbuedLegacyInstance
{
  // Rank
  private int _rank;
  // Tier (UI rank)
  private Integer _uiRank;

  /**
   * Constructor.
   */
  protected NonImbuedLegacyInstance()
  {
    // Nothing
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  protected NonImbuedLegacyInstance(NonImbuedLegacyInstance source)
  {
    super();
    _rank=source._rank;
    _uiRank=source._uiRank;
  }

  /**
   * Get the rank.
   * @return the rank.
   */
  public int getRank()
  {
    return _rank;
  }

  /**
   * Set the rank.
   * @param rank the value to set.
   */
  public void setRank(int rank)
  {
    _rank=rank;
  }

  /**
   * Get the UI rank.
   * @return the UI rank or <code>null</code> if not set.
   */
  public Integer getUiRank()
  {
    return _uiRank;
  }

  /**
   * Set the UI rank.
   * @param uiRank the value to set.
   */
  public void setUiRank(Integer uiRank)
  {
    _uiRank=uiRank;
  }

  /**
   * Get the associated legacy.
   * @return a legacy or <code>null</code> if not defined.
   */
  public abstract AbstractNonImbuedLegacy getLegacy();
}
