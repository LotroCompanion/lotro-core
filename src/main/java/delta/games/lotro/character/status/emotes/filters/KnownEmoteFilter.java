package delta.games.lotro.character.status.emotes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.emotes.EmoteStatus;

/**
 * Filter for emote status that are known, or that are NOT known.
 * @author DAM
 */
public class KnownEmoteFilter implements Filter<EmoteStatus>
{
  private Boolean _isKnown;

  /**
   * Constructor.
   * @param isKnown Indicates if this filter shall select emote statuses
   * that are known, or not (<code>null</code> means no filter).
   */
  public KnownEmoteFilter(Boolean isKnown)
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
  public boolean accept(EmoteStatus status)
  {
    if (_isKnown==null)
    {
      return true;
    }
    boolean acquired=status.isAvailable();
    boolean auto=status.getEmote().isAuto();
    boolean known=acquired||auto;
    return (_isKnown.booleanValue()==known);
  }
}
