package delta.games.lotro.lore.items.legendary.non_imbued;

import delta.games.lotro.common.Effect;

/**
 * Legacy instance on a non-imbued legendary item.
 * @author DAM
 */
public class NonImbuedLegacyInstance
{
  // Effect
  // TODO: use legacy
  private Effect _effect;
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
   * Get the associated legacy.
   * @return the associated legacy.
   */
  public Effect getLegacy()
  {
    return _effect;
  }

  /**
   * Get a displayable label for this legacy.
   * @return a displayable label.
   */
  public String getLabel()
  {
    if (_effect!=null)
    {
      return _effect.getLabel();
    }
    return "?";
  }

  /**
   * Set the associated legacy.
   * @param effect the legacy to set.
   */
  public void setLegacy(Effect effect)
  {
    _effect=effect;
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

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_effect!=null)
    {
      sb.append("(non imbued) ID=").append(_effect.getIdentifier()).append(", ");
    }
    String label=getLabel();
    sb.append(label);
    sb.append(", rank=").append(_rank);
    // Add tier?
    return sb.toString();
  }
}
