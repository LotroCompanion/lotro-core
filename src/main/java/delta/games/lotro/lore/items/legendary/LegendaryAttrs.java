package delta.games.lotro.lore.items.legendary;

/**
 * Attributes of a legendary item model.
 * @author DAM
 */
public class LegendaryAttrs
{
  private int _mainLegacyId;
  private int _mainLegacyBaseRank;

  /**
   * Constructor.
   */
  public LegendaryAttrs()
  {
    _mainLegacyId=0;
    _mainLegacyBaseRank=0;
  }

  /**
   * Get the identifier of the main non-imbued legacy.
   * @return a non-imbued legacy identifier.
   */
  public int getMainLegacyId()
  {
    return _mainLegacyId;
  }

  /**
   * Set the identifier of the main non-imbued legacy.
   * @param mainLegacyId Identifier to set.
   */
  public void setMainLegacyId(int mainLegacyId)
  {
    _mainLegacyId=mainLegacyId;
  }

  /**
   * Get the base rank of the main legacy.
   * @return a rank.
   */
  public int getMainLegacyBaseRank()
  {
    return _mainLegacyBaseRank;
  }

  /**
   * Set the identifier of the main non-imbued legacy.
   * @param baseRank Rank to set.
   */
  public void setMainLegacyBaseRank(int baseRank)
  {
    _mainLegacyBaseRank=baseRank;
  }

  @Override
  public String toString()
  {
    return "Main legacy ID: "+_mainLegacyId+", base rank: "+_mainLegacyBaseRank;
  }
}
