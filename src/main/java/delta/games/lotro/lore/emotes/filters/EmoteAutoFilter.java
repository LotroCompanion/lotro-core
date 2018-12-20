package delta.games.lotro.lore.emotes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Filter for on the 'auto' property of emotes.
 * @author DAM
 */
public class EmoteAutoFilter implements Filter<EmoteDescription>
{
  private Boolean _isAuto;

  /**
   * Constructor.
   * @param isAuto Indicates if this filter shall select emotes
   * that are automatically given or not (<code>null</code> means no filter).
   */
  public EmoteAutoFilter(Boolean isAuto)
  {
    _isAuto=isAuto;
  }

  /**
   * Get the value of the 'auto' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getAutoFlag()
  {
    return _isAuto;
  }

  /**
   * Set the value of the 'auto' flag.
   * @param isAuto Flag to set, may be <code>null</code>.
   */
  public void setAutoFlag(Boolean isAuto)
  {
    _isAuto=isAuto;
  }

  public boolean accept(EmoteDescription emote)
  {
    if (_isAuto==null)
    {
      return true;
    }
    return emote.isAuto()==_isAuto.booleanValue();
  }
}
