package delta.games.lotro.character.status.traits;

import delta.games.lotro.character.traits.TraitDescription;

/**
 * Status of a single trait.
 * @author DAM
 */
public class TraitStatus
{
  private TraitDescription _trait;
  private boolean _available;

  /**
   * Constructor.
   * @param trait Associated trait.
   */
  public TraitStatus(TraitDescription trait)
  {
    if (trait==null)
    {
      throw new IllegalArgumentException("trait is null");
    }
    _trait=trait;
    _available=false;
  }

  /**
   * Get the associated trait.
   * @return the associated trait.
   */
  public TraitDescription getTrait()
  {
    return _trait;
  }

  /**
   * Indicates if this ttait is available or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isAvailable()
  {
    return _available;
  }

  /**
   * Set the 'available' flag.
   * @param available Value to set.
   */
  public void setAvailable(boolean available)
  {
    _available=available;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int traitID=_trait.getIdentifier();
    sb.append("Trait ").append(_trait.getName()).append(" (").append(traitID).append("): ");
    if (_available)
    {
      sb.append("available");
    }
    else
    {
      sb.append("not available");
    }
    return sb.toString();
  }
}
