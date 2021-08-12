package delta.games.lotro.lore.instances;

/**
 * Skirmish group size.
 * @author DAM
 */
public enum SkirmishGroupSize
{
  /**
   * Solo.
   */
  SOLO("Solo"),
  /**
   * Solo.
   */
  DUO("Duo"),
  /**
   * Small fellowship.
   */
  SMALL_FELLOWSHIP("Small fellowship"),
  /**
   * Fellowship.
   */
  FELLOWSHIP("Fellowship"),
  /**
   * Raid (12).
   */
  RAID12("Raid (12)"),
  /**
   * Raid (24).
   */
  RAID24("Raid (24)");

  private String _label;
  private SkirmishGroupSize(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
