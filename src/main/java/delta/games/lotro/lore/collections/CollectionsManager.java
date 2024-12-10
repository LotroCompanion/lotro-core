package delta.games.lotro.lore.collections;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.enums.CollectionCategory;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.collections.io.xml.CollectionsXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for collections access.
 * @author DAM
 */
public class CollectionsManager
{
  private static CollectionsManager _instance=null;

  private HashMap<Integer,CollectionDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static CollectionsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new CollectionsManager();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private CollectionsManager()
  {
    _cache=new HashMap<Integer,CollectionDescription>(100);
    loadAll();
  }

  /**
   * Load all collections.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File collectionsFile=cfg.getFile(DataFiles.COLLECTIONS);
    long now=System.currentTimeMillis();
    List<CollectionDescription> collections=new CollectionsXMLParser().parseCollectionsFile(collectionsFile);
    for(CollectionDescription collection : collections)
    {
      _cache.put(Integer.valueOf(collection.getIdentifier()),collection);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"collections",duration);
  }

  /**
   * Get a list of all collections, sorted by identifier.
   * @return A list of collections.
   */
  public List<CollectionDescription> getAll()
  {
    ArrayList<CollectionDescription> collections=new ArrayList<CollectionDescription>();
    collections.addAll(_cache.values());
    Collections.sort(collections,new IdentifiableComparator<CollectionDescription>());
    return collections;
  }

  /**
   * Get a collection using its identifier.
   * @param id Collection identifier.
   * @return A collection description or <code>null</code> if not found.
   */
  public CollectionDescription getCollection(int id)
  {
    CollectionDescription ret=_cache.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get all collection categories.
   * @return a sorted list of collection categories.
   */
  public List<CollectionCategory> getCategories()
  {
    Set<CollectionCategory> categories=new HashSet<CollectionCategory>();
    for(CollectionDescription collection : _cache.values())
    {
      categories.add(collection.getCategory());
    }
    List<CollectionCategory> ret=new ArrayList<CollectionCategory>(categories);
    Collections.sort(ret,new LotroEnumEntryNameComparator<CollectionCategory>());
    return ret;
  }
}
