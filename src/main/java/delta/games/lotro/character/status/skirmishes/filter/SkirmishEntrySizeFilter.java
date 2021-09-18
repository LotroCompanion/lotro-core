package delta.games.lotro.character.status.skirmishes.filter;

import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.skirmishes.SkirmishEntry;
import delta.games.lotro.common.enums.GroupSize;

/**
 * Filter for a skirmish entries for a given group size.
 * @author DAM
 */
public class SkirmishEntrySizeFilter implements Filter<SkirmishEntry>
{
  private Set<GroupSize> _sizes;

  /**
   * Constructor.
   * @param sizes Sizes to select.
   */
  public SkirmishEntrySizeFilter(Set<GroupSize> sizes)
  {
    _sizes=sizes;
  }

  /**
   * Get the selected sizes.
   * @return A possibly empty but never <code>null</code> set of sizes.
   */
  public Set<GroupSize> getSelectedSizes()
  {
    return _sizes;
  }

  /**
   * Set the sizes to select.
   * @param sizes Sizes to select.
   */
  public void setSizes(Set<GroupSize> sizes)
  {
    _sizes=sizes;
  }

  @Override
  public boolean accept(SkirmishEntry entry)
  {
    return _sizes.contains(entry.getSize());
  }
}
