package delta.games.lotro.common.owner;

/**
 * Base class for owners.
 * @author DAM
 */
public abstract class Owner
{
  /**
   * Get a displayable label for this location.
   * @return a displayable label.
   */
  public abstract String getLabel();

  @Override
  public String toString()
  {
    return getLabel();
  }
}
