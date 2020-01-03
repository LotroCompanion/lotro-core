package delta.games.lotro.lore.items.sets;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.sets.io.xml.ItemsSetXMLParser;

/**
 * Facade for access to item sets.
 * @author DAM
 */
public final class ItemsSetsManager
{
  private static ItemsSetsManager _instance=new ItemsSetsManager();

  private Map<Integer,ItemsSet> _setsById;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ItemsSetsManager getInstance()
  {
    return _instance;
  }

  /**
   * Constructor.
   */
  public ItemsSetsManager()
  {
    _setsById=new HashMap<Integer,ItemsSet>();
    loadAll();
  }

  private void loadAll()
  {
    File fromFile=LotroCoreConfig.getInstance().getFile(DataFiles.SETS);
    loadAllFromFile(fromFile);
  }

  /**
   * Load all sets from a file.
   * @param inputFile Input file.
   */
  private void loadAllFromFile(File inputFile)
  {
    ItemsSetXMLParser parser=new ItemsSetXMLParser();
    List<ItemsSet> sets=parser.parseXML(inputFile);
    for(ItemsSet set : sets)
    {
      registerSet(set);
    }
  }

  /**
   * Register a new set.
   * @param set Set to add.
   */
  public void registerSet(ItemsSet set)
  {
    _setsById.put(Integer.valueOf(set.getIdentifier()),set);
  }

  /**
   * Get a set using its identifier.
   * @param setId Identifier of the set to get.
   * @return A set or <code>null</code> if not found.
   */
  public ItemsSet getSetById(int setId)
  {
    return _setsById.get(Integer.valueOf(setId));
  }

  /**
   * Get all sets.
   * @return a list of all sets.
   */
  public List<ItemsSet> getAll()
  {
    List<ItemsSet> ret=new ArrayList<ItemsSet>();
    ret.addAll(_setsById.values());
    Collections.sort(ret,new IdentifiableComparator<ItemsSet>());
    return ret;
  }

  /**
   * Get the sets for the given item.
   * @param itemId Item to use.
   * @return A possibly empty but never <code>null</code> list of items sets.
   */
  public List<ItemsSet> getSetsForItem(int itemId)
  {
    List<ItemsSet> ret=new ArrayList<ItemsSet>();
    for(ItemsSet set : _setsById.values())
    {
      if (set.hasMember(itemId))
      {
        ret.add(set);
      }
    }
    return ret;
  }
}
