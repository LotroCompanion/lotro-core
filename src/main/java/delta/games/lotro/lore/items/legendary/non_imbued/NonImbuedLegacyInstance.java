package delta.games.lotro.lore.items.legendary.non_imbued;

/**
 * Legacy instance on a non-imbued legendary item.
 * @author DAM
 */
public abstract class NonImbuedLegacyInstance
{
  // Rank
  private int _rank;

  /**
   * Constructor.
   */
  public NonImbuedLegacyInstance()
  {
    // Nothing
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public NonImbuedLegacyInstance(NonImbuedLegacyInstance source)
  {
    super();
    _rank=source._rank;
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
   * Assess the tier using the rank value.
   * @return A tier value.
   */
  public int getTier()
  {
    // TODO mapping rank<->tier.
    return 0;
  }
}
