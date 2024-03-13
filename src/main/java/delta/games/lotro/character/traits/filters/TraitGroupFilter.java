package delta.games.lotro.character.traits.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.enums.TraitGroup;

/**
 * Filter for traits that belong to a trait group.
 * @author DAM
 */
public class TraitGroupFilter implements Filter<TraitDescription>
{
  private TraitGroup _group;

  /**
   * Constructor.
   * @param group Group to filter on, or <code>null</code> to accept all.
   */
  public TraitGroupFilter(TraitGroup group)
  {
    _group=group;
  }

  /**
   * Get the group to use.
   * @return A group or <code>null</code>.
   */
  public TraitGroup getGroup()
  {
    return _group;
  }

  /**
   * Set the group.
   * @param group Group to set, may be <code>null</code>.
   */
  public void setGroup(TraitGroup group)
  {
    _group=group;
  }

  @Override
  public boolean accept(TraitDescription group)
  {
    if (_group==null)
    {
      return true;
    }
    return group.getTraitGroups().contains(_group);
  }
}
