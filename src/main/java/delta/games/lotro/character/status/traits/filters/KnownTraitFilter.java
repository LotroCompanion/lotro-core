package delta.games.lotro.character.status.traits.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.traits.TraitStatus;

/**
 * Filter for trait status that are known, or that are NOT known.
 * @author DAM
 */
public class KnownTraitFilter implements Filter<TraitStatus>
{
  private Boolean _isKnown;

  /**
   * Constructor.
   * @param isKnown Indicates if this filter shall select trait statuses
   * that are known, or not (<code>null</code> means no filter).
   */
  public KnownTraitFilter(Boolean isKnown)
  {
    _isKnown=isKnown;
  }

  /**
   * Get the value of the 'is known' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getIsKnownFlag()
  {
    return _isKnown;
  }

  /**
   * Set the value of the 'is known' flag.
   * @param isKnown Flag to set, may be <code>null</code>.
   */
  public void setIsKnownFlag(Boolean isKnown)
  {
    _isKnown=isKnown;
  }

  @Override
  public boolean accept(TraitStatus status)
  {
    if (_isKnown==null)
    {
      return true;
    }
    boolean known=status.isAvailable();
    return (_isKnown.booleanValue()==known);
  }
}
