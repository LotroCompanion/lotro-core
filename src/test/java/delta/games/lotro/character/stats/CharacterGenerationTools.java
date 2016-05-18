package delta.games.lotro.character.stats;

import java.util.HashMap;
import java.util.List;

import delta.common.utils.NumericTools;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Tools for character generation.
 * @author DAM
 */
public class CharacterGenerationTools
{
  private HashMap<Long,Item> _items;
  private HashMap<Integer,HashMap<String,Item>> _essencesMap;

  /**
   * Constructor.
   */
  public CharacterGenerationTools()
  {
    initItems();
    init();
  }

  private void initItems()
  {
    _items=new HashMap<Long,Item>();
    List<Item> items=ItemsManager.getInstance().getAllItems();
    for(Item item : items)
    {
      _items.put(Long.valueOf(item.getIdentifier()),item);
    }
  }

  private void init()
  {
    _essencesMap=new HashMap<Integer,HashMap<String,Item>>();
    for(Item item : _items.values())
    {
      String category=item.getSubCategory();
      if ((category!=null) && (category.startsWith("Essence:")))
      {
        String tierStr=category.substring(category.length()-1);
        Integer tier=NumericTools.parseInteger(tierStr);
        HashMap<String,Item> mapbyTier=_essencesMap.get(tier);
        if (mapbyTier==null)
        {
          mapbyTier=new HashMap<String,Item>();
          _essencesMap.put(tier,mapbyTier);
        }
        String name=item.getName();
        mapbyTier.put(name,item);
      }
    }
  }

  /**
   * Get an item using its identifier.
   * @param id Identifier.
   * @return An item or <code>null</code> if not found.
   */
  public Item getItemById(long id)
  {
    return _items.get(Long.valueOf(id));
  }

  /**
   * Get an essence using its tier and name.
   * @param tier Tier to get.
   * @param name Essence name.
   * @return An essence item or <code>null</code> if not found.
   */
  public Item getEssenceByName(int tier, String name)
  {
    Item ret=null;
    HashMap<String,Item> mapByTier=_essencesMap.get(Integer.valueOf(tier));
    if (mapByTier!=null)
    {
      ret=mapByTier.get(name);
    }
    return ret;
  }
}
