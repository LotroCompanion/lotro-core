package delta.games.lotro.common.stats;

/**
 * Special effect.
 * @author DAM
 */
public class SpecialEffect
{
  private String _label;

  /**
   * Constructor.
   * @param label Label of the effect.
   */
  public SpecialEffect(String label)
  {
    _label=label;
  }

  /**
   * Get the label for this effect.
   * @return a displayable label.
   */
  public String getLabel()
  {
    return _label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
