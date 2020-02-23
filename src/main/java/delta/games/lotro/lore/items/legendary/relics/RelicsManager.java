package delta.games.lotro.lore.items.legendary.relics;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.legendary.relics.io.xml.RelicXMLParser;
import delta.games.lotro.lore.items.legendary.relics.io.xml.RelicXMLWriter;

/**
 * Facade for relics access.
 * @author DAM
 */
public class RelicsManager
{
  private static RelicsManager _instance;

  private HashMap<Integer,RelicsCategory> _categories;

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
    _categories=new HashMap<Integer,RelicsCategory>();
  }

  /**
   * Get a relic category using its name.
   * @param code Category code.
   * @param doCreate Create category if it does not exist yet.
   * @return A category or <code>null</code>.
   */
  public RelicsCategory getRelicCategory(int code, boolean doCreate)
  {
    Integer key=Integer.valueOf(code);
    RelicsCategory category=_categories.get(key);
    if ((doCreate)&&(category==null))
    {
      category=new RelicsCategory(code);
      _categories.put(key,category);
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
   * Get a relic using its identifier.
   * @param id Identifier.
   * @return A relic or <code>null</code> if not found.
   */
  public Relic getById(int id)
  {
    for(RelicsCategory category : _categories.values())
    {
      Relic relic=category.getById(id);
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
    File relicsFile=cfg.getFile(DataFiles.RELICS);
    RelicXMLParser parser=new RelicXMLParser();
    List<RelicsCategory> categories=parser.parseRelicsFile(relicsFile);
    for(RelicsCategory category : categories)
    {
      _categories.put(Integer.valueOf(category.getCategoryCode()),category);
    }
  }

  /**
   * Get all relic categories.
   * @return a list of categories.
   */
  public List<RelicsCategory> getCategories()
  {
    List<RelicsCategory> categories=new ArrayList<RelicsCategory>();
    categories.addAll(_categories.values());
    return categories;
  }

  /**
   * Get a list of all relics.
   * @return a list of all relics.
   */
  public List<Relic> getAll()
  {
    List<Relic> relics=new ArrayList<Relic>();
    for(RelicsCategory category : _categories.values())
    {
      List<Relic> categoryRelics=category.getAllRelics();
      for(Relic relic : categoryRelics)
      {
        relics.add(relic);
      }
    }
    return relics;
  }

  /**
   * Get a list of all relics.
   * @param type Relic type.
   * @param slot Slot to use.
   * @return a list of all relics.
   */
  public List<Relic> getAllRelics(RelicType type, EquipmentLocation slot)
  {
    List<Relic> relics=new ArrayList<Relic>();
    for(RelicsCategory category : _categories.values())
    {
      List<Relic> categoryRelics=category.getAllRelics();
      for(Relic relic : categoryRelics)
      {
        boolean match=match(relic,type,slot);
        if (match)
        {
          relics.add(relic);
        }
      }
    }
    return relics;
  }

  private boolean match(Relic relic, RelicType type, EquipmentLocation slot)
  {
    boolean ret=relic.hasType(type);
    if (ret)
    {
      ret=relic.isSlotAllowed(slot);
    }
    return ret;
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
