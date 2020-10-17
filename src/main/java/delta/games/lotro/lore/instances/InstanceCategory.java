package delta.games.lotro.lore.instances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.lore.instances.comparators.InstanceCategoryNameComparator;
import delta.games.lotro.lore.instances.comparators.PrivateEncounterNameComparator;

/**
 * Category of instances.
 * @author DAM
 */
public class InstanceCategory
{
  private InstanceCategory _parent;
  private String _name;
  private List<InstanceCategory> _childCategories;
  private List<SkirmishPrivateEncounter> _privateEncounters;

  /**
   * Constructor.
   * @param parent Parent category (may be <code>null</code>).
   * @param name Category name.
   */
  public InstanceCategory(InstanceCategory parent, String name)
  {
    _parent=parent;
    _name=name;
    _childCategories=new ArrayList<InstanceCategory>();
    _privateEncounters=new ArrayList<SkirmishPrivateEncounter>();
  }

  /**
   * Get the parent category.
   * @return A category or <code>null</code>.
   */
  public InstanceCategory getParent()
  {
    return _parent;
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
   * Add a child instance category.
   * @param category Category to add.
   */
  public void addInstanceCategory(InstanceCategory category)
  {
    _childCategories.add(category);
  }

  /**
   * Get the child categories.
   * @return a list of categories.
   */
  public List<InstanceCategory> getChildCategories()
  {
    List<InstanceCategory> ret=new ArrayList<InstanceCategory>(_childCategories);
    Collections.sort(ret,new InstanceCategoryNameComparator());
    return ret;
  }

  /**
   * Get a child category by its name.
   * @param name Name to use.
   * @return A category or <code>null</code> if not found.
   */
  public InstanceCategory getChildByName(String name)
  {
    for(InstanceCategory childCategory : _childCategories)
    {
      if (childCategory.getName().equals(name))
      {
        return childCategory;
      }
    }
    return null;
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
