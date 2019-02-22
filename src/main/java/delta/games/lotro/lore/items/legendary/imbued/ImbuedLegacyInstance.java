package delta.games.lotro.lore.items.legendary.imbued;

/**
 * Imbued legacy instance.
 * @author DAM
 */
public class ImbuedLegacyInstance
{
  /**
   * XP amount for each tier.
   */
  private static final int XP_FOR_TIER=30000;
  private ImbuedLegacy _legacy;
  private int _xp;
  private int _unlockedLevels;

  /**
   * Constructor.
   */
  public ImbuedLegacyInstance()
  {
    _legacy=null;
    _xp=0;
    _unlockedLevels=0;
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public ImbuedLegacyInstance(ImbuedLegacyInstance source)
  {
    _legacy=source._legacy;
    _xp=source._xp;
    _unlockedLevels=source._unlockedLevels;
  }

  /**
   * Get the associated legacy.
   * @return the associated legacy.
   */
  public ImbuedLegacy getLegacy()
  {
    return _legacy;
  }

  /**
   * Set the associated legacy.
   * @param legacy the legacy to set.
   */
  public void setLegacy(ImbuedLegacy legacy)
  {
    _legacy=legacy;
  }

  /**
   * Get the acquired XP on this legacy.
   * @return an XP amount.
   */
  public int getXp()
  {
    return _xp;
  }

  /**
   * Set the amount of acquired XP.
   * @param xp the XP amount to set.
   */
  public void setXp(int xp)
  {
    _xp=xp;
  }

  /**
   * Get the number of unlocked levels.
   * @return the number of unlocked levels.
   */
  public int getUnlockedLevels()
  {
    return _unlockedLevels;
  }

  /**
   * Set the number of unlocked levels.
   * @param unlockedLevels the value to set.
   */
  public void setUnlockedLevels(int unlockedLevels)
  {
    _unlockedLevels=unlockedLevels;
  }

  /**
   * Get the current tier for this legacy.
   * @return a tier value.
   */
  public int getCurrentTier()
  {
    int tier=1+(_xp/XP_FOR_TIER);
    return tier;
  }

  /**
   * Get the current maximum tier for this legacy.
   * @return a tier value.
   */
  public int getCurrentMaxTier()
  {
    int initialMaxLevel=_legacy.getMaxInitialLevel();
    int currentMaxTiers=initialMaxLevel+_unlockedLevels;
    return currentMaxTiers;
  }

  /**
   * Get a displayable label for this legacy.
   * @return a displayable label.
   */
  public String getLabel()
  {
    if (_legacy!=null)
    {
      return _legacy.getLabel();
    }
    return "?";
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    String label=getLabel();
    sb.append(label);
    sb.append(", XP=").append(_xp);
    sb.append(", unlocked levels=").append(_unlockedLevels);
    sb.append(", current tier=").append(getCurrentTier());
    sb.append(", current max tier=").append(getCurrentMaxTier());
    // Add tier?
    return sb.toString();
  }
}
