package delta.games.lotro.character.status.portraitFrames.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.filters.NamedFilter;
import delta.games.lotro.lore.portraitFrames.PortraitFrameDescription;

/**
 * Portrait frame status filter.
 * @author DAM
 */
public class PortraitFramesStatusFilter implements Filter<PortraitFrameDescription>
{
  private NamedFilter<PortraitFrameDescription> _nameFilter;
  private Boolean _selectedState;

  /**
   * Constructor.
   */
  public PortraitFramesStatusFilter()
  {
    // Name
    _nameFilter=new NamedFilter<PortraitFrameDescription>();
  }

  /**
   * Get the filter on name.
   * @return a name filter.
   */
  public NamedFilter<PortraitFrameDescription> getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the selected state.
   * @return a state.
   */
  public Boolean getSelectedState()
  {
    return _selectedState;
  }

  /**
   * Set the selected state.
   * @param selectedState State to set.
   */
  public void setSelectedState(Boolean selectedState)
  {
    _selectedState=selectedState;
  }

  @Override
  public boolean accept(PortraitFrameDescription item)
  {
    return _nameFilter.accept(item);
  }
}
