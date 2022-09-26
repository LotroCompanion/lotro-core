package delta.games.lotro.lore.items.paper.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.items.paper.PaperItem;

/**
 * Filter for paper items that are account shared or not.
 * @author DAM
 */
public class PaperItemIsAccountSharedFilter implements Filter<PaperItem>
{
  private Boolean _shared;

  /**
   * Constructor.
   * @param shared Indicates if this filter shall select paper items
   * that are shared or not (<code>null</code> means no filter).
   */
  public PaperItemIsAccountSharedFilter(Boolean shared)
  {
    _shared=shared;
  }

  /**
   * Get the value of the 'shared' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getSharedFlag()
  {
    return _shared;
  }

  /**
   * Set the value of the 'shared' flag.
   * @param shared Flag to set, may be <code>null</code>.
   */
  public void setShared(Boolean shared)
  {
    _shared=shared;
  }

  @Override
  public boolean accept(PaperItem paperItem)
  {
    if (_shared==null)
    {
      return true;
    }
    return _shared.booleanValue()==paperItem.isShared();
  }
}
