package delta.games.lotro.lore.items.legendary;

/**
 * Legacy type.
 * @author DAM
 */
public enum LegacyType
{
  /**
   * Stat legacy.
   */
  STAT("Stat"),
  /**
   * Class skill legacy.
   */
  CLASS("Class"),
  /**
   * DPS legacy.
   */
  DPS("DPS"),
  /**
   * Fury.
   */
  FURY("Fury"),
  /**
   * Outgoing healing legacy.
   */
  OUTGOING_HEALING("Tactical Healing Rating"),
  /**
   * Incoming healing legacy.
   */
  INCOMING_HEALING("Incoming Healing Rating"),
  /**
   * Tactical DPS legacy.
   */
  TACTICAL_DPS("Tactical Damage Rating");

  private String _label;

  private LegacyType(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
