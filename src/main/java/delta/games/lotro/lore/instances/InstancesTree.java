package delta.games.lotro.lore.instances;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.instances.io.xml.InstancesTreeXMLParser;

/**
 * Manager for the instances tree.
 * @author DAM
 */
public class InstancesTree
{
  private static InstancesTree _instance=null;
  private InstanceCategory _rootCategory;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static InstancesTree getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  private static InstancesTree load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.INSTANCES_TREE);
    return new InstancesTreeXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public InstancesTree()
  {
    _rootCategory=new InstanceCategory(null,"");
  }

  /**
   * Get the root category.
   * @return the root category.
   */
  public InstanceCategory getRoot()
  {
    return _rootCategory;
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
   * Get a list of all instances.
   * @return a list of all instances.
   */
  public List<SkirmishPrivateEncounter> getInstances()
  {
    List<SkirmishPrivateEncounter> instances=new ArrayList<SkirmishPrivateEncounter>();
    handleCategory(_rootCategory,instances);
    return instances;
  }

  private void handleCategory(InstanceCategory category, List<SkirmishPrivateEncounter> instances)
  {
    for(SkirmishPrivateEncounter pe : category.getPrivateEncounters())
    {
      instances.add(pe);
    }
    for(InstanceCategory child : category.getChildCategories())
    {
      handleCategory(child,instances);
    }
  }

  /**
   * Get all instance categories.
   * @return a sorted list of instance categories.
   */
  public List<String> getCategories()
  {
    Set<String> categories=new HashSet<String>();
    for(SkirmishPrivateEncounter instance : getInstances())
    {
      categories.add(instance.getCategory());
    }
    List<String> ret=new ArrayList<String>(categories);
    Collections.sort(ret);
    return ret;
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
