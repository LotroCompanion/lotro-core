package delta.games.lotro.character.status.collections.birds.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.collections.birds.BirdStatus;

/**
 * Filter for birds that are known, or that are NOT known.
 * @author DAM
 */
public class KnownBirdFilter implements Filter<BirdStatus>
{
  private Boolean _isKnown;

  /**
   * Constructor.
   * @param isKnown Indicates if this filter shall select skill statuses
   * that are known, or not (<code>null</code> means no filter).
   */
  public KnownBirdFilter(Boolean isKnown)
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
  public boolean accept(BirdStatus status)
  {
    if (_isKnown==null)
    {
      return true;
    }
    boolean known=status.isKnown();
    return (_isKnown.booleanValue()==known);
  }
}
