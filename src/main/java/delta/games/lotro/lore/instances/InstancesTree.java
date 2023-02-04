package delta.games.lotro.lore.instances;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.common.utils.io.streams.IndentableStream;
import delta.games.lotro.common.enums.WJEncounterCategory;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;
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
  private Map<String,InstanceCategory> _categories;

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
    _categories=new HashMap<String,InstanceCategory>();
  }

  /**
   * Add a category.
   * @param category Category to add.
   */
  public void addCategory(InstanceCategory category)
  {
    _categories.put(category.getName(),category);
  }

  /**
   * Get a category by name.
   * @param categoryName Name of the category to get.
   * @return A category or <code>null</code> if not found.
   */
  public InstanceCategory getCategory(String categoryName)
  {
    return _categories.get(categoryName);
  }

  /**
   * Get a list of all instances.
   * @return a list of all instances.
   */
  public List<SkirmishPrivateEncounter> getInstances()
  {
    List<SkirmishPrivateEncounter> instances=new ArrayList<SkirmishPrivateEncounter>();
    for(String categoryName : getCategorieNames())
    {
      InstanceCategory category=_categories.get(categoryName);
      handleCategory(category,instances);
    }
    return instances;
  }

  private void handleCategory(InstanceCategory category, List<SkirmishPrivateEncounter> instances)
  {
    for(SkirmishPrivateEncounter pe : category.getPrivateEncounters())
    {
      instances.add(pe);
    }
  }

  /**
   * Get the category names.
   * @return A sorted list of names.
   */
  public List<String> getCategorieNames()
  {
    List<String> ret=new ArrayList<String>(_categories.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get all instance categories.
   * @return a sorted list of instance categories.
   */
  public List<WJEncounterCategory> getInstanceCategories()
  {
    Set<WJEncounterCategory> categories=new HashSet<WJEncounterCategory>();
    for(SkirmishPrivateEncounter instance : getInstances())
    {
      categories.add(instance.getCategory());
    }
    List<WJEncounterCategory> ret=new ArrayList<WJEncounterCategory>(categories);
    Collections.sort(ret,new LotroEnumEntryNameComparator<WJEncounterCategory>());
    return ret;
  }

  /**
   * Dump all instances, sorted by category.
   */
  public void dump()
  {
    List<String> categoryNames=getCategorieNames();
    IndentableStream out=new IndentableStream(System.out);
    for(String categoryName : categoryNames)
    {
      InstanceCategory category=_categories.get(categoryName);
      dumpCategory(out,category);
    }
  }

  private void dumpCategory(IndentableStream out, InstanceCategory category)
  {
    out.println(category.getName());
    out.incrementIndendationLevel();
    for(SkirmishPrivateEncounter skirmishPE : category.getPrivateEncounters())
    {
      out.println(skirmishPE.getName());
    }
    out.decrementIndentationLevel();
  }
}
