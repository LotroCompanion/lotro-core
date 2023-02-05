package delta.games.lotro.lore.instances;

import delta.games.lotro.common.Named;

/**
 * Entry in the instances tree.
 * @author DAM
 */
public class InstanceTreeEntry implements Named
{
  private String _categoryName;
  private SkirmishPrivateEncounter _privateEncounter;

  /**
   * Constructor.
   * @param categoryName Category name.
   * @param privateEncounter Private encounter.
   */
  public InstanceTreeEntry(String categoryName, SkirmishPrivateEncounter privateEncounter)
  {
    _categoryName=categoryName;
    _privateEncounter=privateEncounter;
  }

  @Override
  public String getName()
  {
    return _categoryName;
  }

  /**
   * Get the managed instance.
   * @return the managed instance.
   */
  public SkirmishPrivateEncounter getInstance()
  {
    return _privateEncounter;
  }
}
