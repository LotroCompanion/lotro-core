package delta.games.lotro.lore.items.legendary;

/**
 * Base class for all legacies.
 * @author DAM
 */
public class AbstractLegacy
{
  private LegacyType _type;

  /**
   * Constructor.
   */
  public AbstractLegacy()
  {
    _type=LegacyType.STAT;
  }

  /**
   * Get the legacy type.
   * @return a legacy type.
   */
  public LegacyType getType()
  {
    return _type;
  }

  /**
   * Set the legacy type.
   * @param type the type to set.
   */
  public void setType(LegacyType type)
  {
    _type=type;
  }
}
