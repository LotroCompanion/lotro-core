package delta.games.lotro.lore.items.legendary;

/**
 * Attributes of a legendary item model.
 * @author DAM
 */
public class LegendaryAttrs
{
  private int _mainLegacyId;
  private int _combatDPSLevel;
  private int _combatPropertyModLevel;

  /**
   * Constructor.
   */
  public LegendaryAttrs()
  {
    _mainLegacyId=0;
    _combatDPSLevel=0;
    _combatPropertyModLevel=0;
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
   * Get the 'combat DPS level'.
   * @return a level.
   */
  public int getCombatDPSLevel()
  {
    return _combatDPSLevel;
  }

  /**
   * Set the 'combat DPS level'.
   * @param combatDPSLevel Level to set.
   */
  public void setCombatDPSLevel(int combatDPSLevel)
  {
    _combatDPSLevel=combatDPSLevel;
  }

  /**
   * Get the 'combat property mod level'.
   * @return a level.
   */
  public int getCombatPropertyModLevel()
  {
    return _combatPropertyModLevel;
  }

  /**
   * Set the 'combat property mod level'.
   * @param combatPropertyModLevel Level to set.
   */
  public void setCombatPropertyModLevel(int combatPropertyModLevel)
  {
    _combatPropertyModLevel=combatPropertyModLevel;
  }

  /**
   * Get the base rank of the main legacy.
   * @return a rank.
   */
  public int getMainLegacyBaseRank()
  {
    return (_combatDPSLevel!=0)?_combatDPSLevel:_combatPropertyModLevel;
  }

  @Override
  public String toString()
  {
    return "Main legacy ID: "+_mainLegacyId+", DPS level: "+_combatDPSLevel+", Property mod level: "+_combatPropertyModLevel;
  }
}
