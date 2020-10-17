package delta.games.lotro.lore.instances;

import java.util.List;

/**
 * Manager for instance categories.
 * @author DAM
 */
public class InstanceCategories
{
  private InstanceCategory _rootCategory;

  /**
   * Constructor.
   */
  public InstanceCategories()
  {
    _rootCategory=new InstanceCategory(null,"");
  }

  /**
   * Get/build a category from its path.
   * @param path Category path.
   * @return an instance category.
   */
  public InstanceCategory getFromPath(String[] path)
  {
    InstanceCategory parent=_rootCategory;
    for(String pathItem : path)
    {
      InstanceCategory category=parent.getChildByName(pathItem);
      if (category==null)
      {
        category=new InstanceCategory(parent,pathItem);
        parent.addInstanceCategory(category);
      }
      parent=category;
    }
    return parent;
  }

  /**
   * Dump all instances, sorted by category.
   */
  public void dump()
  {
    List<InstanceCategory> rootCategories=_rootCategory.getChildCategories();
    for(InstanceCategory rootCategory : rootCategories)
    {
      dumpCategory(0,rootCategory);
    }
  }

  private void dumpCategory(int level, InstanceCategory category)
  {
    for(int i=0;i<level;i++) System.out.print('\t');
    System.out.println(category.getName());
    List<InstanceCategory> children=category.getChildCategories();
    if (children.size()>0)
    {
      for(InstanceCategory childCategory : children)
      {
        dumpCategory(level+1,childCategory);
      }
    }
    else
    {
      for(SkirmishPrivateEncounter skirmishPE : category.getPrivateEncounters())
      {
        for(int i=0;i<level+1;i++) System.out.print('\t');
        System.out.println(skirmishPE.getName());
      }
    }
  }
}
