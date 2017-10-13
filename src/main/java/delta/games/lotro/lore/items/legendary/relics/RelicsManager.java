package delta.games.lotro.lore.items.legendary.relics;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.LotroCoreConfig;
import delta.games.lotro.lore.items.legendary.relics.io.xml.RelicXMLParser;
import delta.games.lotro.lore.items.legendary.relics.io.xml.RelicXMLWriter;

/**
 * Facade for relics access.
 * @author DAM
 */
public class RelicsManager
{
  private static RelicsManager _instance;

  private List<RelicsCategory> _categoriesList;
  private HashMap<String,RelicsCategory> _categories;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static RelicsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new RelicsManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public RelicsManager()
  {
    _categoriesList=new ArrayList<RelicsCategory>();
    _categories=new HashMap<String,RelicsCategory>();
  }

  /**
   * Get a relic category using its name.
   * @param name Category name.
   * @param doCreate Create category if it does not exist yet.
   * @return A category or <code>null</code>.
   */
  public RelicsCategory getRelicCategory(String name, boolean doCreate)
  {
    RelicsCategory category=_categories.get(name);
    if ((doCreate)&&(category==null))
    {
      category=new RelicsCategory(name);
      _categories.put(name,category);
      _categoriesList.add(category);
    }
    return category;
  }

  /**
   * Get a relic by its name.
   * @param name Name of the relic to get.
   * @return A relic or <code>null</code> if not found.
   */
  public Relic getByName(String name)
  {
    for(RelicsCategory category : _categories.values())
    {
      Relic relic=category.getByName(name);
      if (relic!=null)
      {
        return relic;
      }
    }
    return null;
  }

  /**
   * Load all relics.
   */
  private void loadAll()
  {
    _categories.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File loreDir=cfg.getLoreDir();
    File relicsFile=new File(loreDir,"relics.xml");
    RelicXMLParser parser=new RelicXMLParser();
    List<RelicsCategory> categories=parser.parseRelicsFile(relicsFile);
    for(RelicsCategory category:categories)
    {
      RelicsCategory ownCategory=getRelicCategory(category.getName(),true);
      List<Relic> relics=category.getAllRelics();
      for(Relic relic:relics)
      {
        ownCategory.addRelic(relic);
      }
    }
  }

  /**
   * Get all relic category names.
   * @return a list of category names.
   */
  public List<String> getCategories()
  {
    List<String> categories=new ArrayList<String>();
    for(RelicsCategory category : _categoriesList)
    {
      categories.add(category.getName());
    }
    Collections.sort(categories);
    return categories;
  }

  /**
   * Get a list of all relics.
   * @return a list of all relics.
   */
  public List<Relic> getAllRelics()
  {
    List<Relic> relics=new ArrayList<Relic>();
    for(Map.Entry<String,RelicsCategory> entry : _categories.entrySet())
    {
      relics.addAll(entry.getValue().getAllRelics());
    }
    return relics;
  }

  /**
   * Write a file with items.
   * @param toFile Output file.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeRelicsFile(File toFile)
  {
    RelicXMLWriter writer=new RelicXMLWriter();
    boolean ok=writer.writeRelics(toFile,this,EncodingNames.UTF_8);
    return ok;
  }
}
