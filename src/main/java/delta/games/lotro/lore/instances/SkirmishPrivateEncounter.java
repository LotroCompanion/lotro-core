package delta.games.lotro.lore.instances;

import delta.common.utils.text.EndOfLine;


/**
 * Private encounter of type 'Skirmish/Big battle/Classic instance'.
 * @author DAM
 */
public class SkirmishPrivateEncounter extends PrivateEncounter
{
  // Rewards configuration
  // Difficulty tiers
  // Group sizes
  // Min/max level scale
  // Category (Defensive...) // WorldJoin_EncounterCategory
  // Type (Skirmish...) // WorldJoin_EncounterType
  // Skirmish_RestrictionDescription: #1: You must have completed the 'Helm's Dike' battle.
  private Integer _levelScaling;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public SkirmishPrivateEncounter(int id)
  {
    super(id);
  }

  /**
   * Get the character level scaling for this instance.
   * @return A level or <code>null</code>.
   */
  public Integer getLevelScaling()
  {
    return _levelScaling;
  }

  /**
   * Set the character level scaling.
   * @param levelScaling Level to set (may be <code>null</code>).
   */
  public void setLevelScaling(Integer levelScaling)
  {
    _levelScaling=levelScaling;
  }

  protected void dump(StringBuilder sb)
  {
    super.dump(sb);
    if (_levelScaling!=null)
    {
      sb.append("Level scaling=").append(_levelScaling).append(EndOfLine.NATIVE_EOL);
    }
  }
}
