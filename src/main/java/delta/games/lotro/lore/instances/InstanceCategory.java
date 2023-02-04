package delta.games.lotro.lore.instances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.Named;
import delta.games.lotro.lore.instances.comparators.PrivateEncounterNameComparator;

/**
 * Category of instances.
 * @author DAM
 */
public class InstanceCategory implements Named
{
  private String _name;
  private List<SkirmishPrivateEncounter> _privateEncounters;

  /**
   * Constructor.
   * @param name Category name.
   */
  public InstanceCategory(String name)
  {
    _name=name;
    _privateEncounters=new ArrayList<SkirmishPrivateEncounter>();
  }

  /**
   * Get the category name.
   * @return a category name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Add an instance in this category.
   * @param pe Instance to add.
   */
  public void addPrivateEncounter(SkirmishPrivateEncounter pe)
  {
    _privateEncounters.add(pe);
  }

  /**
   * Get the instances in this category.
   * @return a list of instances.
   */
  public List<SkirmishPrivateEncounter> getPrivateEncounters()
  {
    List<SkirmishPrivateEncounter> ret=new ArrayList<SkirmishPrivateEncounter>(_privateEncounters);
    Collections.sort(ret,new PrivateEncounterNameComparator());
    return ret;
  }
}
